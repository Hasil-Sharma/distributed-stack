package com.ds.commands;

import io.atomix.copycat.Command;

public class SPushCommand implements Command<FTStackResult> {
    private final Object stackId;
    private final Object item;

    public SPushCommand(Object stackId, Object item) {
        this.stackId = stackId;
        this.item = item;
    }

    public Object getStackId() {
        return stackId;
    }

    public Object getItem() {
        return item;
    }
}
