package com.ds.commands;

import io.atomix.copycat.Command;

public class SPopCommand implements Command<Object>{
    private final Object stackId;

    public SPopCommand(Object stackId) {
        this.stackId = stackId;
    }

    public Object getStackId() {
        return stackId;
    }
}
