/*
 * Schuetze Consulting Informationssysteme AG, Berlin
 */
package org.netbeans.modules.bamboo.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


/**
 * @author schroeder
 */
public class AllPlansResponseITest {
    private AllPlansResponse classUnderTest;

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        classUnderTest = new AllPlansResponse();

        List<Plan> planList = new ArrayList<>();
        Plan plan = new Plan();
        plan.setName("test");
        planList.add(plan);

        Plans plans = new Plans();
        plans.setSize(planList.size());
        plans.setPlan(planList);
        classUnderTest.setPlans(plans);

        mapper = new ObjectMapper();
    }

    /**
     * Test of getPlans method, of class AllPlansResponse.
     */
    @Test
    public void testSerialize_NotEmpty() throws JsonProcessingException {
        String result = mapper.writeValueAsString(classUnderTest);
        assertFalse(result.isEmpty());
    }

    /**
     * Test of getPlans method, of class AllPlansResponse.
     */
    @Test
    public void testDeserialize_NotEmpty() throws JsonProcessingException, IOException {
        String str =
            "{\"expand\":null,\"link\":null,\"plans\":{\"size\":1,\"plan\":" +
            "[{\"shortName\":null,\"shortKey\":null,\"type\":null,\"enabled\":false,\"link\":null,\"key\":null,\"name\":\"test\"}]" +
            ",\"start-index\":0,\"max-result\":0}}";
        AllPlansResponse result = mapper.readValue(str, AllPlansResponse.class);
        assertFalse(result.getPlans().getPlan().isEmpty());
    }
}
