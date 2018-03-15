package com.ds.commands;

import io.atomix.copycat.Command;

public class SCreateCommand implements Command<FTStackResult>{
    private final Object label;

    public SCreateCommand(Object label){
        this.label = label;
    }

    public Object getLabel() {
        return label;
    }
}
