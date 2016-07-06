package org.netbeans.modules.bamboo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllResultsResponse extends AbstractAllResponse {
    private Results results;

    public static class Builder {
        private final AllResultsResponse response = new AllResultsResponse();

        public Builder results(final Results results) {
            response.results = results;

            return this;
        }

        public AllResultsResponse build() {
            return response;
        }
    }
}
