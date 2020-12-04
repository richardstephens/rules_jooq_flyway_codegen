package dev.richst.jooq_bazel;

import org.testcontainers.containers.JdbcDatabaseContainer;

import java.io.IOException;

public class TestContainersJdbcProvider implements JdbcProvider {

    private final JdbcDatabaseContainer jdbcContainer;

    public TestContainersJdbcProvider(JdbcDatabaseContainer jdbcContainer) {
        this.jdbcContainer = jdbcContainer;
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
