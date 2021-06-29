package dev.richst.jooq_bazel;

import org.flywaydb.core.Flyway;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Jdbc;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class JooqBazelCodegen {
    public static void main(String[] argv) throws Exception {
        if (argv.length != 3) {
            System.err.println("ERR: Codegen params missing");
            System.exit(1);
        }

        String outputSourceJar = argv[0];
        String dbContainerType = argv[1];
        String codeGenConfigXmlPath = argv[2];

        Path path = Files.createTempDirectory("jooq-codegen");
        try (JdbcProvider jdbcContainer = newJdbcContainerOfType(dbContainerType)) {
            prepareDatabase(jdbcContainer);

            Jdbc jdbc = buildJdbcConfig(jdbcContainer);

            Configuration configuration =
                    buildGenerationToolConfigurationWithOverrides(codeGenConfigXmlPath, jdbc, path);
            new GenerationTool().run(configuration);

            ZipUtil zipUtil = new ZipUtil();
            zipUtil.zipDirectory(path.toFile(), outputSourceJar);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        } finally {
            recursiveDeleteOnExit(path);
        }
    }

    private static JdbcProvider newJdbcContainerOfType(String dbContainerType) {
        if ("postgres".equals(dbContainerType)) {
            return new TestContainersJdbcProvider(new PostgreSQLContainer<>());
        } else if ("mariadb".equals(dbContainerType)) {
            return new TestContainersJdbcProvider(new MariaDBContainer<>());
        } else if ("mysql".equals(dbContainerType)) {
            return new TestContainersJdbcProvider(new MySQLContainer<>());
        } else if ("sqlite".equals(dbContainerType)) {
            return new SqliteJdbcProvider();
        } else {
            throw new IllegalArgumentException("Unrecognised JDBC container type");
        }
    }

    private static Configuration buildGenerationToolConfigurationWithOverrides(
            String codeGenConfigXmlPath, Jdbc jdbc, Path path) throws IOException {
        Configuration configuration =
                GenerationTool.load(new FileInputStream(codeGenConfigXmlPath));
        configuration.getGenerator().getTarget().setDirectory(path.toAbsolutePath().toString());
        configuration.setJdbc(jdbc);
        return configuration;
    }

    private static void prepareDatabase(JdbcProvider jdbcContainer) {
        jdbcContainer.start();
        Flyway flyway =
                Flyway.configure()
                        .dataSource(
                                jdbcContainer.getJdbcUrl(),
                                jdbcContainer.getUsername(),
                                jdbcContainer.getPassword())
                        .load();
        flyway.migrate();
    }

    private static Jdbc buildJdbcConfig(JdbcProvider jdbcContainer) {
        Jdbc jdbc = new Jdbc();
        jdbc.setDriver(jdbcContainer.getDriverClassName());
        jdbc.setUrl(jdbcContainer.getJdbcUrl());
        jdbc.setUser(jdbcContainer.getUsername());
        jdbc.setPassword(jdbcContainer.getPassword());
        return jdbc;
    }

    public static void recursiveDeleteOnExit(Path path) throws IOException {
        Files.walkFileTree(
                path,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        file.toFile().deleteOnExit();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        dir.toFile().deleteOnExit();
                        return FileVisitResult.CONTINUE;
                    }
                });
    }
}
