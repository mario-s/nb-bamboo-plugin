package org.netbeans.modules.bamboo.client.rest;

/**
 * This enum contains a range of valid HTTP response codes.
 *
 * @author spindizzy
 */
enum HttpResponseCode {

    Successful(200, 206), Redirect(300, 308), Unauthorized(401, 401), Unknown(-1, -1);

    private final int min;
    private final int max;

    private HttpResponseCode(int min, int max) {
        this.min = min;
        this.max = max;
    }

    static HttpResponseCode getCode(int status) {
        for (HttpResponseCode code : values()) {
            if (status >= code.min && status <= code.max) {
                return code;
            }
        }
        return Unknown;
    }

}
