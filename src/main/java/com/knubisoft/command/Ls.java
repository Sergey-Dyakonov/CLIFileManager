package com.knubisoft.command;

import java.io.File;
import java.util.List;

public class Ls extends Command{

    public Ls(Context context) {
        super(context);
    }

    @Override
    public String execute(List<String> args) {
        //TODO : format output
        File curDir = context.getCurrentDir();
        File[] allFiles = curDir.listFiles();
        StringBuilder res = new StringBuilder();
        if (allFiles != null) {
            for (File file : allFiles) {
                res.append(file.getName()).append(":").append(file.getUsableSpace()).append("\n");
            }
        }
        return res.toString();
    }
}
