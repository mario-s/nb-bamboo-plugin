package org.netbeans.modules.bamboo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Results extends Metrics {
    private List<Result> result;
    private String expand;

    public static class Builder {
        private final Results result = new Results();

        public Builder addResult(final Result result) {
            newResults();

            this.result.result.add(0, result);

            return this;
        }

        private void newResults() {
            if (result.result == null) {
                result.result = new ArrayList<>();
            }
        }

        public Results build() {
            return result;
        }

        public Builder results(final List<Result> newResults) {
            result.result = newResults;

            return this;
        }

        public Builder expand(final String expand) {
            result.expand = expand;

            return this;
        }
    }
}
