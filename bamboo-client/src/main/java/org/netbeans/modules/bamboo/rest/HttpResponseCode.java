package org.netbeans.modules.bamboo.rest;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * This enum contains a range of valid HTTP response codes.
 * @author spindizzy
 */
enum HttpResponseCode {
    
    Successful(200,206), Redirect(300,308), Unauthorized(401,401);
    
    private int min;
    private int max;

    private HttpResponseCode(int min, int max){
        this.min = min;
        this.max = max;
    }

    static Optional<HttpResponseCode> getCode(int status){
        for(HttpResponseCode code : values()){
            if(status >= code.min && status <= code.max){
                return of(code);
            }
        }
        return empty();
    }
    
}
