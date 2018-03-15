package com.ds.commands;

import io.atomix.copycat.Query;

public class SSizeCommand implements Query<Object> {
    private final Object stackId;

    public SSizeCommand(Object stackId) {
        this.stackId = stackId;
    }

    public Object getStackId() {
        return stackId;
    }
}
