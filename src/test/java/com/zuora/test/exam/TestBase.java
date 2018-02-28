package com.zuora.test.exam;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.zuora.test.exam.utils.Utils;

public class TestBase {

    /**
     * Send the GET request, and deserialize the response to object of user
     * specified type.
     *
     * @param url
     * @param params
     *            - The query parameters attached to base URL.
     * @param classOfT
     *            - The deserialization class type. If null, null will be
     *            returned.
     * @return
     * @throws IOException
     */
    protected static <T> T get(String url, Map<String, String> params, Class<T> classOfT) throws IOException {
        Response resp = connect(Utils.createUrl(url, params)).ignoreHttpErrors(true).execute();
        return parseResponse(resp, classOfT);
    }

    /**
     * Send the POST request, and deserialize the response to object of user
     * specified type.
     *
     * @param url
     * @param jsonData
     *            - the request body, with type of application/json
     * @param classOfT
     *            - The deserialization class type. If null, null will be
     *            returned.
     * @return
     * @throws IOException
     */
    protected static <T> T post(String url, String jsonData, Class<T> classOfT) throws IOException {
        Connection conn = connect(url).requestBody(jsonData).header("Content-Type", "application/json");
        Response resp = conn.ignoreHttpErrors(true).method(Method.POST).execute();
        return parseResponse(resp, classOfT);
    }

    /**
     * Send the DELETE request.
     *
     * @param url
     * @throws IOException
     */
    protected static void delete(String url) throws IOException {
        Response resp = connect(url).ignoreHttpErrors(true).method(Method.DELETE).execute();
        if (resp.statusCode() != 204) {
            int sc = resp.statusCode();
            String msg = resp.statusMessage();
            throw new IOException("Failed request: " + sc + " - " + msg);
        }
    }

    /**
     * Send the PUT request. , and deserialize the response to object of user
     * specified type.
     *
     * @param url
     * @param jsonData
     * @param classOfT
     * @return
     * @throws IOException
     */
    protected static <T> T put(String url, String jsonData, Class<T> classOfT) throws IOException {
        Connection conn = connect(url).requestBody(jsonData).header("Content-Type", "application/json");
        Response resp = conn.ignoreHttpErrors(true).method(Method.PUT).execute();
        return parseResponse(resp, classOfT);
    }

    protected static Connection connect(String url) {
        Connection conn = Jsoup.connect(url);

        Charset cs = StandardCharsets.UTF_8;
        Encoder enc = Base64.getEncoder();

        String username = Config.apiUsername();
        String password = Config.apiPassword();
        byte[] bytes = (username + ":" + password).getBytes(cs);
        conn.header("Accept", "application/json");
        conn.header("Authorization", "Basic " + new String(enc.encode(bytes), cs));

        int timeout = Config.resourceTimeout();
        String userAgent = Config.userAgent();
        return conn.timeout(timeout).userAgent(userAgent).ignoreContentType(true);
    }

    private static <T> T parseResponse(Response resp, Class<T> classOfT) throws IOException {
        if (resp.statusCode() != 200) {
            int sc = resp.statusCode();
            String msg = resp.statusMessage();
            throw new IOException("Failed request: " + sc + " - " + msg);
        }
        // System.out.println(resp.body());
        return classOfT == null ? null : new Gson().fromJson(resp.body(), classOfT);
    }
}
