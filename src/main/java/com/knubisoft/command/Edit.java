package com.knubisoft.command;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Edit extends Command {

    Scanner scanner = new Scanner(System.in);

    public Edit(Context context) {
        super(context);
    }

    @Override
    @SneakyThrows
    public String execute(List<String> args) {
        if (!ArgsUtils.hasFirstArg(args)) {
            return ArgsUtils.firstParamMissingMessage(this.getClass());
        } else {
            File file = new File(context.getCurrentDir(), args.get(0));
            if (!file.exists()) {
                return "The file does not exist or cannot be found";
            }
            if (file.isDirectory()) {
                return "The file is directory. Cannot be edited";
            }
            if (!file.canWrite()) {
                return "Missing permissions. Cannot be edited";
            }
            System.out.print("Type the content to append. To finish, type '!wq'\n--> ");
            StringBuilder contentToAppend = new StringBuilder();
            String content;
            while (!(content = scanner.nextLine()).equals("!wq")) {
                contentToAppend.append(content);
                System.out.print("--> ");
            }
            Files.write(Path.of(args.get(0)), contentToAppend.toString().getBytes(), StandardOpenOption.APPEND);
        }
        return "";
    }
}
