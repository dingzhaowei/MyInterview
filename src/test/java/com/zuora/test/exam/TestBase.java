package com.zuora.test.exam;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.zuora.test.exam.utils.Utils;

public class TestBase {

    protected Gson gson = new Gson();

    /**
     * Send the GET request, and deserialize the response to object of user
     * specified type.
     *
     * @param url
     * @param params
     *            - The query parameters attached to base URL
     * @param classOfT
     * @return
     * @throws IOException
     */
    protected <T> T get(String url, Map<String, String> params, Class<T> classOfT) throws IOException {
        Response resp = connect(Utils.createUrl(url, params)).ignoreHttpErrors(true).execute();
        System.out.println(resp.body());
        return gson.fromJson(resp.body(), classOfT);
    }

    protected Connection connect(String url) {
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
}
