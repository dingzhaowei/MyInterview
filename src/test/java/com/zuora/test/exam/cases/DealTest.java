package com.zuora.test.exam.cases;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.zuora.test.exam.Config;
import com.zuora.test.exam.TestBase;
import com.zuora.test.exam.model.Deal;

public class DealTest extends TestBase {

    private static final String DEAL_API_URL = Config.apiUrlRoot() + "/opportunity";

    private static final int DEAL_LIST_PAGE_SIZE = 10;

    private static List<Deal> dealsForTests = new ArrayList<>();

    private static ExecutorService executor = Executors.newFixedThreadPool(15);

    @BeforeClass
    public static void setUp() throws IOException {
        Deal[] deals = null;
        try (Reader reader = Config.getResourceAsReader("testdata/deals.json")) {
            deals = new Gson().fromJson(reader, Deal[].class);
        }

        // Use multi-threads to create to save time
        Arrays.stream(deals).map(deal -> executor.submit(() -> {
            return post(DEAL_API_URL, new Gson().toJson(deal), Deal.class);
        })).collect(Collectors.toList()).forEach(future -> {
            try {
                dealsForTests.add(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Assert.assertEquals("Failed to add all deals for tests", deals.length, dealsForTests.size());
    }

    @AfterClass
    public static void tearDown() throws IOException {
        Map<String, List<String>> m = new HashMap<>();
        m.put("ids", dealsForTests.stream().map(deal -> "" + deal.getId()).collect(Collectors.toList()));
        Connection conn = connect(DEAL_API_URL + "/bulk");
        conn.requestBody(new Gson().toJson(m)).header("Content-Type", "application/x-www-form-urlencoded");
        Response resp = conn.ignoreHttpErrors(true).method(Method.POST).execute();
        Assert.assertEquals("Bulk deal deletion failed", 204, resp.statusCode());
        System.out.println("All deals for tests have been deleted successfully");
    }

    @Test
    public void testListingDeals() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("page_size", "" + DEAL_LIST_PAGE_SIZE);
        params.put("cursor", "");
        Deal[] deals = get(DEAL_API_URL, params, Deal[].class);

        for (int i = 0; i < deals.length; i++) {
            deals[i].assertFieldsValid("Deal " + i + " of list");
        }
        Assert.assertEquals("The deal list size is not as expected", DEAL_LIST_PAGE_SIZE, deals.length);
    }

    @Test
    public void testGetDealbyID() throws Exception {
        Deal toFetch = dealsForTests.get(0);
        Deal fetched = get(DEAL_API_URL + "/" + toFetch.getId(), null, Deal.class);
        fetched.assertFieldsValid("Fetched deal " + fetched.getId());
        fetched.assertEqualsWith(toFetch, "id", "name", "expected_value", "probability", "milestone");
    }

    @Test
    public void testDeleteDeal() throws Exception {
        Deal toDelete = dealsForTests.get(dealsForTests.size() - 1);
        delete(DEAL_API_URL + "/" + toDelete.getId());
    }

    @Test
    public void testCreateDealToContactUsingEamilID() throws Exception {
        Deal toCreate = new Deal();
        toCreate.setName("ForTest-CreateDealToContactUsingEamilID");
        toCreate.setExpected_value(500.0);
        toCreate.setProbability(75);
        toCreate.setClose_date(System.currentTimeMillis() / 1000 + 1000000);
        toCreate.setMilestone("Proposal");

        String email = Config.apiUsername();
        Deal created = post(DEAL_API_URL + "/email/" + email, new Gson().toJson(toCreate), Deal.class);
        created.assertFieldsValid("Created deal " + created.getId());
        created.assertEqualsWith(toCreate, "name", "expected_value", "probability", "milestone");
        Assert.assertEquals("Owner is not as expected", email, created.getOwner().get("email").getAsString());

        delete(DEAL_API_URL + "/" + created.getId());
    }

    @Test
    public void testPartialUpdate() throws Exception {
        Deal toUpdate = dealsForTests.get(1);
        toUpdate.setExpected_value(600.0);
        toUpdate.setProbability(60);
        Deal updated = put(DEAL_API_URL + "/partial-update", new Gson().toJson(toUpdate), Deal.class);

        updated.assertFieldsValid("Updated deal " + updated.getId());
        Assert.assertEquals("Probability's not updated", toUpdate.getProbability(), updated.getProbability());
        Assert.assertEquals("Expected value's not updated", toUpdate.getExpected_value(), updated.getExpected_value());
    }

}
