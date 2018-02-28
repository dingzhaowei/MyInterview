package com.zuora.test.exam;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.zuora.test.exam.utils.UnicodeInputStream;

public final class Config {

    private static final String CONFIG_FILE = "test.properties";

    private static Config instance = new Config();

    private Properties props;

    private String apiUrlRoot;

    private String apiUsername;

    private String apiPassword;

    private String userAgent;

    private int resourceTimeout;

    private Config() {
        initialize();
    }

    public static String apiUrlRoot() {
        return instance.apiUrlRoot;
    }

    public static String apiUsername() {
        return instance.apiUsername;
    }

    public static String apiPassword() {
        return instance.apiPassword;
    }

    public static String userAgent() {
        return instance.userAgent;
    }

    public static int resourceTimeout() {
        return instance.resourceTimeout;
    }

    public static Reader getResourceAsReader(String resName) throws IOException {
        InputStream in = new UnicodeInputStream(getResourceAsStream(resName));
        return new InputStreamReader(in, "UTF-8");
    }

    private void initialize() {
        try (Reader reader = getResourceAsReader(CONFIG_FILE)) {
            props = new Properties();
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
        apiUrlRoot = getValue("apiUrlRoot");
        apiUsername = getValue("apiUsername");
        apiPassword = getValue("apiPassword");
        userAgent = getValue("userAgent");
        resourceTimeout = getIntValue("resourceTimeout");
    }

    private String getValue(String key) {
        return props.getProperty(key);
    }

    private int getIntValue(String key) {
        String value = getValue(key);
        return value == null ? 0 : Integer.parseInt(value);
    }

    @SuppressWarnings("unused")
    private long getLongValue(String key) {
        String value = getValue(key);
        return value == null ? 0L : Long.parseLong(value);
    }

    @SuppressWarnings("unused")
    private boolean getBooleanValue(String key) {
        String value = getValue(key);
        return value == null ? false : Boolean.parseBoolean(value);
    }

    private static Path getResource(String resName) {
        String userDir = System.getProperty("user.dir");
        return Paths.get(userDir, resName.split("/"));
    }

    private static InputStream getResourceAsStream(String resName) throws IOException {
        return Files.newInputStream(getResource(resName));
    }
}
