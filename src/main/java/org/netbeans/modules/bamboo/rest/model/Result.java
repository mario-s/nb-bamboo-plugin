package org.netbeans.modules.bamboo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    private Link link;
    private String key;
    private State state;
    private Plan plan;
    private LifeCycleState lifeCycleState;
    private int number;
    private int id;
    private String buildReason;

    public static class Builder {

        private final Result result = new Result();

        public Builder number(final int number) {
            result.number = number;

            return this;
        }

        public Builder id(final int id) {
            result.id = id;

            return this;
        }

        public Builder state(final State state) {
            result.state = state;

            return this;
        }

        public Builder lifeCycleState(final LifeCycleState lifeCycleState) {
            result.lifeCycleState = lifeCycleState;

            return this;
        }

        public Result build() {
            return result;
        }
    }
}
