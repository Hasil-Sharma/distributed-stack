package com.ds.commands;

import io.atomix.copycat.Command;

public class SCreateCommand implements Command<Object>{
    private final Object label;

    public SCreateCommand(Object label){
        this.label = label;
    }

    public Object getLabel() {
        return label;
    }
}
