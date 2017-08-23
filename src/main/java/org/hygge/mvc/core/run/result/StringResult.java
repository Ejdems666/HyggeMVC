package org.hygge.mvc.core.run.result;

/**
 * Created by adam on 8/23/2017.
 */
public class StringResult implements Result<String> {
    private String result;

    public StringResult(String result) {
        this.result = result;
    }

    @Override
    public String getResult() {
        return result;
    }
}
