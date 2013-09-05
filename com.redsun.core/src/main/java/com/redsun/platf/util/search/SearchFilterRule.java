package com.redsun.platf.util.search;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class SearchFilterRule implements Serializable {
    public static final long serialVersionUID = 21L;
    public String field;
    public String op;
    public String data;

    public SearchFilterRule() {
    }

    public SearchFilterRule(String field, String op, String data) {
        this.field = field;
        this.op = op;
        this.data = data;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    @Override
      	public String toString() {
      		return ToStringBuilder.reflectionToString(this);
      	}
}