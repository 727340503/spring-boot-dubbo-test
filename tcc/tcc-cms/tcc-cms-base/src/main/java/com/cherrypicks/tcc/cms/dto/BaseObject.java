package com.cherrypicks.tcc.cms.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BaseObject implements Serializable {

    private static final long serialVersionUID = -3536120305647717643L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
