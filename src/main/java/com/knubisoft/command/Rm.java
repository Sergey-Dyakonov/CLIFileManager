package com.knubisoft.command;

import java.io.File;
import java.util.List;

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
        delete(file, ArgsUtils.hasFlags(args, "-r", "--recursive"));
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
