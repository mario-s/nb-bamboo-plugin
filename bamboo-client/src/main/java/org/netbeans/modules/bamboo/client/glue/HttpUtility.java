/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.client.glue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import lombok.extern.java.Log;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * This utility provides HTTP related functionality.
 *
 * @author Mario Schroeder
 */
@Log
public final class HttpUtility {

    private static final String WRONG_URL = "url is wrong: %s";

    /**
     * Checks if the server behind the url answers with a valid http response.
     *
     * @param url the url to the server.
     * @return true if exists
     */
    public boolean exists(String url) {
        boolean exists = false;
        if (isNotBlank(url)) {
            try {
                URL endpoint = new URL(url);
                URLConnection connection = endpoint.openConnection();
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) connection;
                    int status = httpConn.getResponseCode();
                    exists = isValid(status);
                }
            } catch (IOException ex) {
                log.info(format(WRONG_URL, ex.getMessage()));
            }
        }
        return exists;
    }

    private boolean isValid(int status) {
        HttpResponseCode code = HttpResponseCode.getCode(status);
        return code.equals(HttpResponseCode.Successful) || code.equals(HttpResponseCode.Redirect) || code.equals(
                HttpResponseCode.Unauthorized);

    }
}
