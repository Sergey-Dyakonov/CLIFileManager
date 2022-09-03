package com.knubisoft.command;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Mkfile extends Command{

    public Mkfile(Context context) {
        super(context);
    }

    @Override
    @SneakyThrows
    public String execute(List<String> args) {
        if(!ArgsUtils.hasFirstArg(args)){
            return ArgsUtils.firstParamMissingMessage(this.getClass());
        }
        File file = new File(context.getCurrentDir(), args.get(0));
        if(file.exists()){
            return "The file already exists";
        }
        Files.createFile(Path.of(file.getPath()));
        return "";
    }
}
