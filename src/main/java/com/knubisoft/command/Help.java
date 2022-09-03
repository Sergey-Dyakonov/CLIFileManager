package com.knubisoft.command;

import java.util.List;
import java.util.Map;

public class Help extends Command{

    public Help(Context context) {
        super(context);
    }

    @Override
    public String execute(List<String> args) {
        Map<String, Command> commands = context.getCommandMap();

        StringBuilder res = new StringBuilder("Available commands:\n");
        if (commands != null) {
            for (String s : commands.keySet()) {
                res.append(s).append("\n");
            }
        }
        return res.toString();
    }
}
