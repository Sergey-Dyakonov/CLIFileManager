package com.knubisoft;

import com.knubisoft.command.Command;
import com.knubisoft.command.Context;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Context context = new Context(null, new File(System.getProperty("user.dir")));
        Map<String, Command> commands = getCommands(context);
        context.setCommandMap(commands);

        System.out.println("Hi there! Press 'q' or 'exit' to quit");
        performCommands(context, commands);
    }

    private static void performCommands(Context context, Map<String, Command> commands){
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.print("--> ");
            String inputLine = scan.nextLine();
            if(StringUtils.isBlank(inputLine)){
                continue;
            }

            List<String> allArgs = Arrays.asList(inputLine.split(" "));

            String commandName = allArgs.get(0);
            if(commandName.equals("q") || commandName.equals("exit")){
                System.out.println("Bye bye");
                break;
            }

            Command command = commands.getOrDefault(commandName, new Command(context){
                public String execute(List<String> args){
                    return "Command " + commandName + " is unknown";
                }
            });
            System.out.println(command.execute(allArgs.subList(1, allArgs.size())));
        }
    }

    @SneakyThrows
    private static Map<String, Command> getCommands(Context context){
        Reflections reflections = new Reflections("com.knubisoft.command", Scanners.SubTypes);
        Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);

        Map<String, Command> commandNameToFunction = new LinkedHashMap<>();
        for(Class<? extends Command> each : allClasses){
            Command instance = each.getDeclaredConstructor(Context.class).newInstance(context);
            commandNameToFunction.put(each.getSimpleName().toLowerCase(), instance);
        }
        return commandNameToFunction;
    }
}
