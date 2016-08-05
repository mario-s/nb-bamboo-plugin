package org.netbeans.modules.bamboo.glue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

/**
 *
 * @author spindizzy
 */
public class HttpUtility {

    private final Logger log;

    public HttpUtility() {
        this.log = Logger.getLogger(HttpUtility.class.getName());
    }

    /**
     * Checks if the server behind the url answers with a valid http response.
     *
     * @param url the url to the server.
     * @return true if exists
     */
    public boolean exists(String url) {
        boolean exists = false;
        try {
            URL endpoint = new URL(url);
            URLConnection connection = endpoint.openConnection();
            if (connection instanceof HttpURLConnection) {
                HttpURLConnection httpConn = (HttpURLConnection) connection;
                int status = httpConn.getResponseCode();
                exists = isValid(status);
            }
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
        return exists;
    }

    private boolean isValid(int status) {
        return isSuccessful(status) || isRedirect(status) || isUnauthorized(status);
    }
    
    private boolean isSuccessful(int status) {
        return status >= 200 && status <= 206;
    }
    
    private boolean isRedirect(int status) {
        return status >= 300 && status <= 308;
    }
    
    private boolean isUnauthorized(int status) {
        return status == 401;
    }
}
