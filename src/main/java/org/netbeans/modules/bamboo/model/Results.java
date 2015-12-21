package org.netbeans.modules.bamboo.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Results extends Metrics {

    @JsonProperty("result")
    private List<Result> results;
    private String expand;

    public List<Result> getResults() {
        return results;
    }

    public String getExpand() {
        return expand;
    }

    public static class Builder {

        private final Results result = new Results();

        public Builder addResult(final Result result) {
            newResults();

            this.result.results.add(0, result);

            return this;
        }

        private void newResults() {
            if (result.results == null) {
                result.results = new ArrayList<>();
            }
        }

        public Results build() {
            return result;
        }

        public Builder results(final List<Result> newResults) {
            result.results = newResults;

            return this;
        }

        public Builder expand(final String expand) {
            result.expand = expand;

            return this;
        }
    }
}
