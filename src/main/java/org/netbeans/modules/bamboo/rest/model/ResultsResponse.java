package org.netbeans.modules.bamboo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultsResponse extends AbstractResponse {
    private Results results;

    public static class Builder {
        private final ResultsResponse response = new ResultsResponse();

        public Builder results(final Results results) {
            response.results = results;

            return this;
        }

        public ResultsResponse build() {
            return response;
        }
    }
}
