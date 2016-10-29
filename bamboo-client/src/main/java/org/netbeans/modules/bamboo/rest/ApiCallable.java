package org.netbeans.modules.bamboo.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.entity;

/**
 * This interface describes a way to access the API.
 * @author spindizzy
 */
interface ApiCallable<T> {
    
    /**
     * Performs a GET request and returns the expected object of T.
     * @param target the target to be called
     * @return the result
     */
    T doGet(final WebTarget target);
    
     /**
     * Simple doPost without any values
     * @param target the target to be called
     * @return the response code
     */
    default Response doPost(WebTarget target) {
        Form form = new Form();
        Entity<Form> entity = entity(form, MediaType.WILDCARD_TYPE);
        return target.request().post(entity);
    }
}
