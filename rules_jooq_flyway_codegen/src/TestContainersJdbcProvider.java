package dev.richst.jooq_bazel;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TestContainersJdbcProvider implements JdbcProvider {

    private final JdbcDatabaseContainer jdbcContainer;

    private TestContainersJdbcProvider(JdbcDatabaseContainer jdbcContainer) {
        this.jdbcContainer = jdbcContainer;
    }

    public static TestContainersJdbcProvider forClass(Class<? extends JdbcDatabaseContainer> clazz, String imgName, String defaultImage) {
        try {
            if (imgName == null || imgName.equals("--")) {
                for (Constructor c : clazz.getConstructors()) {
                    if (c.getParameterCount() == 0) {
                        return new TestContainersJdbcProvider((JdbcDatabaseContainer) c.newInstance());
                    }
                }
            } else {
                for (Constructor c : clazz.getConstructors()) {
                    if (c.getParameterCount() == 1 && c.getParameterTypes()[0].equals(DockerImageName.class)) {
                        DockerImageName tcImgName = DockerImageName.parse(imgName).asCompatibleSubstituteFor(defaultImage);
                        return new TestContainersJdbcProvider((JdbcDatabaseContainer) c.newInstance(tcImgName));
                    }
                }
            }
            throw new IllegalStateException("Could not find appropriate constructor on " + clazz.getCanonicalName());
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("ReflectiveOperationException trying to create testcontainers instance",ex);
        }
    }

    @Override
    public void start() {
        this.jdbcContainer.start();
    }

    @Override
    public String getDriverClassName() {
        return this.jdbcContainer.getDriverClassName();
    }

    @Override
    public String getJdbcUrl() {
        return this.jdbcContainer.getJdbcUrl();
    }

    @Override
    public String getUsername() {
        return this.jdbcContainer.getUsername();
    }

    @Override
    public String getPassword() {
        return this.jdbcContainer.getPassword();
    }

    @Override
    public void close() throws IOException {
        jdbcContainer.close();
    }
}
