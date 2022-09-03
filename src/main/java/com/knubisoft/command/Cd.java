package com.knubisoft.command;

import java.io.File;
import java.util.List;

public class Cd extends Command{

    public Cd(Context context) {
        super(context);
    }

    @Override
    public String execute(List<String> args) {
        if(args.isEmpty()){
            return "Incorrect argument. Use '..' to navigate to parent directory";
        } else {
            String direction = args.get(0);
            File currentDir = context.getCurrentDir();
            if(direction.equals("..")){
                context.setCurrentDir(currentDir.getParentFile());
            } else {
                File child = new File(currentDir, direction);
                if(!child.exists()){
                    return "Child directory " + direction + " does not exist";
                } else {
                    context.setCurrentDir(child);
                }
            }
        }
        return "";
    }
}
