package com.knubisoft.command;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Rm extends Command {

    public Rm(Context context) {
        super(context);
    }

    @Override
    public String execute(List<String> args) {
        if (!ArgsUtils.hasFirstArg(args)) {
            return ArgsUtils.firstParamMissingMessage(this.getClass());
        }
        File file = new File(context.getCurrentDir(), args.get(0));
        if (ArgsUtils.hasFlags(args)) {
            if (args.stream().anyMatch(s -> s.equals("-r") || s.equals("--recursive"))) {
                delete(file, true);
            } else {
                return ArgsUtils.unknownFlags(args.stream().
                        filter(s -> !s.equals("-r") && !s.equals("--recursive")).
                        collect(Collectors.toList()));
            }
        } else {
            delete(file, false);
        }
        return "";
    }

    private void delete(File file, boolean recursivly) {
        if (recursivly && file.isDirectory() && file.listFiles() != null) {
            for (File f : file.listFiles()) {
                delete(f, true);
            }
        }
        if (!file.exists()) {
            System.out.println("The file does not exist or cannot be found");
        }
        file.delete();
    }
}
