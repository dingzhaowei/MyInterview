package com.zuora.test.exam.cases;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.zuora.test.exam.Config;
import com.zuora.test.exam.TestBase;
import com.zuora.test.exam.model.Deal;

public class DealTest extends TestBase {

    private static final String DEAL_API_URL = Config.apiUrlRoot() + "/opportunity";

    private static final int DEAL_LIST_PAGE_SIZE = 10;

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

}
