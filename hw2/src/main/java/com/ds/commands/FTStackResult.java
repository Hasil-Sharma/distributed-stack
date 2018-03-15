package com.ds.commands;

import java.io.Serializable;

public class FTStackResult implements Serializable{
    private Object result;
    private Boolean error;
    private String errorString;

    public FTStackResult(Object result) {
        this.result = result;
        this.error = false;
        this.errorString = "";
    }

    public FTStackResult(Boolean error, String errorString) {
        this.result = null;
        this.error = error;
        this.errorString = errorString;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FTStackResult{");
        if (!error) sb.append("result=").append(result);
        else {
            sb.append("error=").append(error);
            sb.append(", errorString='").append(errorString).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}
