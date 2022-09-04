package com.knubisoft;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.knubisoft.command.Command;
import com.knubisoft.command.Context;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.File;
import java.util.*;

public class Main {

    /* FIXME
    //DONE 1. View file (TXT) in console
    //DONE 2. Add text to the end of file
    //DONE 3. Add text to specific location **
    //DONE 4. LS   --  Табличкой выводилось
    //DONE 5.  LS size / r / w / extension -- srwe
    //DONE 6. Mkdir
    //DONE 7. Mkfile
    //DONE 8. Rm file / Rm dir
    //DONE 9. Rm -r -- recursive
    //TODO 10. LS tree 2/3/4/5 .. n  DEPTH -- view file system as a tree  *** (отредактировано)
    -----------------------------
    //TODO 11. Clear the console
    //TODO 12. Detailed description to commands
    //TODO 13. Add approving of removing. If with flag -f -- then remove without approving
    //TODO 14. Add ability to read file names with spaces (for reading file name with spaces type file name in '' Ex. 'New file')
    //TODO 15. Add ability to read flags in different places
    //TODO 16. ZIP / UNZIP
    * */
    @SneakyThrows
    public static void main(String[] args) {
        Context context = new Context(null, new File(System.getProperty("user.dir")));
        Map<String, Command> commands = getCommands(context);
        context.setCommandMap(commands);

        System.out.println("Hi there! Press 'q' or 'exit' to quit");
        performCommands(context, commands);
    }

    private static void performCommands(Context context, Map<String, Command> commands) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("--> ");
            String inputLine = scan.nextLine();
            if (StringUtils.isBlank(inputLine)) {
                continue;
            }

            List<String> allArgs = Arrays.asList(inputLine.split(" "));

            String commandName = allArgs.get(0);
            if (commandName.equals("q") || commandName.equals("exit")) {
                System.out.println("Bye bye");
                break;
            }

            Command command = commands.getOrDefault(commandName, new Command(context) {
                public String execute(List<String> args) {
                    return "Command " + commandName + " is unknown. Press 'help' to know about commands";
                }
            });
            System.out.println(command.execute(allArgs.subList(1, allArgs.size())));
        }
    }

    @SneakyThrows
    private static Map<String, Command> getCommands(Context context) {
        Reflections reflections = new Reflections("com.knubisoft.command", Scanners.SubTypes);
        Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);

        Map<String, Command> commandNameToFunction = new LinkedHashMap<>();
        for (Class<? extends Command> each : allClasses) {
            Command instance = each.getDeclaredConstructor(Context.class).newInstance(context);
            commandNameToFunction.put(each.getSimpleName().toLowerCase(), instance);
        }
        return commandNameToFunction;
    }
}
