package com.knubisoft.command;

import java.util.List;

public class ArgsUtils {

    public static String unknownFlags(List<String> flag) {
        return "Unknown flag " + flag.toString();
    }

    public static boolean hasFirstArg(List<String> args) {
        return args != null && !args.isEmpty() && args.get(0) != null || !args.get(0).isBlank();
    }

    public static boolean hasFlags(List<String> args) {
        return args != null && !args.isEmpty() && args.stream().anyMatch(s -> s.contains("-"));
    }

    public static String firstParamMissingMessage(Class<? extends Command> command) {
        return "Argument missing. Try " + Command.COLOR_KEYS.ANSI_GREEN + command.getSimpleName().toLowerCase() + " anyname" + Command.COLOR_KEYS.ANSI_RESET;
    }
}
