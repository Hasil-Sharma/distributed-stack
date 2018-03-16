package com.ds.commands;

import io.atomix.copycat.Query;

public class STopCommand implements Query<FTStackResult> {
    private final Object stackId;

    public STopCommand(Object stackId) {
        this.stackId = stackId;
    }

    public Object getStackId() {
        return stackId;
    }
}
