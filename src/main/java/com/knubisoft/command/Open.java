package com.knubisoft.command;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

public class Open extends Command {

    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Open(Context context) {
        super(context);
    }

    @SneakyThrows
    public String execute(List<String> args) {
        //TODO implement Open
        if (args == null || args.isEmpty()) {
            return "Invalid input for open command. Ex: " + ANSI_CYAN + "open C:\\Users\\User\\Documents\\MyDocument.txt\n"
                    + ANSI_RESET + "Press 'help open' for detailed information";
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
                file.setReadable(true);
                return FileUtils.readFileToString(file, "utf8");
            } catch (NullPointerException e) {
                return "The file does not exist";
            }
        }
    }
}
