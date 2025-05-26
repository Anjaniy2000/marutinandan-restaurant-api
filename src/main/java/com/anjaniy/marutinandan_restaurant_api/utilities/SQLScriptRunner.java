package com.anjaniy.marutinandan_restaurant_api.utilities;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statements;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class SQLScriptRunner {
    public static void main(String[] args) {
        try {
            Properties props = loadProperties("application.properties");
            String url = props.getProperty("spring.datasource.url");
            String user = props.getProperty("spring.datasource.username");
            String pass = props.getProperty("spring.datasource.password");

            validateDbProps(url, user, pass);

            String sql = readFile("src/main/resources/migrations/changes.sql");
            Statements statements = CCJSqlParserUtil.parseStatements(sql);

            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 Statement stmt = conn.createStatement()) {

                for (var parsedStmt : statements.getStatements()) {
                    String query = parsedStmt.toString();
                    try {
                        stmt.execute(query);
                        System.out.println("✅ Executed: " + query);
                    } catch (Exception ex) {
                        System.err.println("❌ Failed to execute: " + query);
                        System.err.println("   Reason: " + ex.getMessage());
                    }
                }

                System.out.println("✅ All SQL statements executed.");
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Properties loadProperties(String fileName) throws Exception {
        Properties props = new Properties();
        try (InputStream in = SQLScriptRunner.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) throw new RuntimeException("Missing " + fileName);
            props.load(in);
        }
        return props;
    }

    private static void validateDbProps(String url, String user, String pass) {
        if (url == null || user == null || pass == null) {
            throw new IllegalArgumentException("Database configuration is incomplete.");
        }
    }

    private static String readFile(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) throw new RuntimeException("File not found: " + path);
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}
