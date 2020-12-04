package dev.richst.jooq_bazel;

import java.io.Closeable;

public interface JdbcProvider extends Closeable {
    void start();
    String getDriverClassName();
    String getJdbcUrl();
    String getUsername();
    String getPassword();
}
