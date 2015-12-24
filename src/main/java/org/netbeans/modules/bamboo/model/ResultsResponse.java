package org.netbeans.modules.bamboo.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultsResponse {
    private Results results;
    private String expand;
    private Link link;

    public Results getResults() {
        return results;
    }

    public void setResults(final Results results) {
        this.results = results;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(final String expand) {
        this.expand = expand;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(final Link link) {
        this.link = link;
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
