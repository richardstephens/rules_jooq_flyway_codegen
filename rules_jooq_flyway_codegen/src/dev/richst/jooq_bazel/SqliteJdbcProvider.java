package rules_jooq_flyway_codegen.src.dev.richst.jooq_bazel;

import java.io.File;
import java.io.IOException;

public class SqliteJdbcProvider implements JdbcProvider {

    private final File sqliteTempFile;
    public SqliteJdbcProvider() {
        try {
            this.sqliteTempFile = File.createTempFile("bazel_jooq_flyway_codegen", ".sqlite");
        } catch (IOException e) {
            throw new IllegalStateException("IO Exception creating temp file",e);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public String getDriverClassName() {
        return "org.sqlite.JDBC";
    }

    @Override
    public String getJdbcUrl() {
        return "jdbc:sqlite:" + sqliteTempFile.getAbsolutePath();
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public void close() throws IOException {
    }
}
