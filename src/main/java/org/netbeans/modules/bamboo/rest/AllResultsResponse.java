package org.netbeans.modules.bamboo.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AllResultsResponse extends AbstractAllResponse {
    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(final Results results) {
        this.results = results;
    }

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
