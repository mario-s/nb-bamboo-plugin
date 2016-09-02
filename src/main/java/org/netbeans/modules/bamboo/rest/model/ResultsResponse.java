package org.netbeans.modules.bamboo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultsResponse extends AbstractResponse<Result> {
    private Results results;
    
    @Override
    public Collection<Result> asCollection(){
        Collection<Result> coll = new ArrayList<>();
        if(results != null){
            coll.addAll(results.getResult());
        }
        return coll;
    }

    @Override
    public int getMaxResult() {
        return (results != null) ? results.getMaxResult() : 0;
    }

    @Override
    public int getSize() {
        return (results != null) ? results.getSize() : 0;
    }


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
