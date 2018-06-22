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

/**
 * This enum contains a range of valid HTTP response codes.
 *
 * @author Mario Schroeder
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
