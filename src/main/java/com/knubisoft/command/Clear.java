package com.knubisoft.command;

import jline.console.ConsoleReader;
import lombok.SneakyThrows;

import java.io.Console;
import java.io.PrintStream;
import java.util.List;

public class Clear extends Command {

    public Clear(Context context) {
        super(context);
    }


    @Override
    @SneakyThrows
    public String execute(List<String> args) {
        //TODO implement
        return "";
    }
}
