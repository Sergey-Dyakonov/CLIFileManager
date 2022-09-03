package com.knubisoft.command;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

public class Open extends Command {

    public Open(Context context) {
        super(context);
    }

    @SneakyThrows
    public String execute(List<String> args) {
        if (!ArgsUtils.hasFirstArg(args)) {
            return ArgsUtils.firstParamMissingMessage(this.getClass());
        } else {
            try {
                File file = new File(context.getCurrentDir(), args.get(0));
                if (!file.exists()) {
                    return "The file does not exist or cannot be found";
                }
                if (!file.canRead()) {
                    return "Missing permissions. The file cannot be read";
                }
                if (file.isDirectory()) {
                    return "The file is directory. Cannot be opened";
                }
                return FileUtils.readFileToString(file, "utf8");
            } catch (NullPointerException e) {
                return "The file does not exist";
            }
        }
    }
}
