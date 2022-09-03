package com.knubisoft.command;

import java.io.File;
import java.util.List;

public class Mkdir extends Command {

    public Mkdir(Context context) {
        super(context);
    }

    @Override
    public String execute(List<String> args) {
        if(!ArgsUtils.hasFirstArg(args)){
            return ArgsUtils.firstParamMissingMessage(this.getClass());
        }
        File file = new File(context.getCurrentDir(), args.get(0));
        if(file.exists()){
            return "The directory already exists";
        }
        file.mkdir();
        return "";
    }
}
