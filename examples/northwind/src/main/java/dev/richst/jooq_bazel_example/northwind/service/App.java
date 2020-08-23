package dev.richst.jooq_bazel_example.northwind.service;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dev.richst.jooq_bazel_example.northwind.db.*;
import dev.richst.jooq_bazel_example.northwind.db.test.tables.Employees;
import dev.richst.jooq_bazel_example.northwind.db.test.tables.records.EmployeesRecord;

import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.testcontainers.containers.MySQLContainer;

import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] argv) {
        try {
            DSLContext dsl = startAndMigrateDb();
            registerUrls(dsl);
        } catch (Exception e) {
            System.out.println("Exception on startup");
            e.printStackTrace();
        }
    }

    private static void registerUrls(DSLContext dsl) {
        System.out.println("registering urls");

        Spark.get(
                "/api/employees",
                (req, res) -> {
                    res.type("application/json");
                    return new Gson()
                            .toJson(
                                    dsl.selectFrom(Employees.EMPLOYEES).fetch().stream()
                                            .map(r -> r.intoMap())
                                            .collect(Collectors.toList()));
                });
        Spark.post(
                "/api/employees",
                (req, res) -> {
                    res.type("application/json");
                    EmployeesRecord employeesRecord = dsl.newRecord(Employees.EMPLOYEES);
                    Map<String, String> reqBody = new Gson().fromJson(req.body(), HashMap.class);
                    employeesRecord.setFirstName(reqBody.get("first_name"));
                    employeesRecord.setLastName(reqBody.get("last_name"));
                    employeesRecord.setNotes(reqBody.get("notes"));
                    employeesRecord.setAddress(reqBody.get("address"));
                    employeesRecord.setCity(reqBody.get("city"));
                    employeesRecord.store();
                    return new Gson().toJson(employeesRecord.intoMap());
                });
    }

    private static DSLContext startAndMigrateDb() throws Exception {
        System.out.println("starting db");
        MySQLContainer dbContainer = new MySQLContainer<>();
        dbContainer.start();

        System.out.println("Migrating");
        Flyway flyway =
                Flyway.configure()
                        .dataSource(
                                dbContainer.getJdbcUrl(),
                                dbContainer.getUsername(),
                                dbContainer.getPassword())
                        .load();
        flyway.migrate();

        System.out.println("Connecting to database");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbContainer.getJdbcUrl());
        config.setUsername(dbContainer.getUsername());
        config.setPassword(dbContainer.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        return DSL.using(ds, SQLDialect.MYSQL);
    }
}
