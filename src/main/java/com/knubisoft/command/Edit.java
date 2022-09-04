package com.knubisoft.command;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

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
            if (ArgsUtils.isRWAccessable(file)) {
                if (ArgsUtils.hasFlags(args, "-a", "--append")) {
                    appendContentToFile(args);
                } else {
                    String newContent = initTextEditor(FileUtils.readFileToString(file, "utf8"));
                    FileUtils.writeStringToFile(file, newContent, "utf8");
                }
            }
        }
        return "";
    }

    @SneakyThrows
    private String initTextEditor(String content) {
        final String[] result = new String[1];
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        try (Screen screen = terminalFactory.createScreen()) {
            screen.startScreen();
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow("Press 'Esc' to finish editing");
            Panel contentPanel = new Panel();
            TextBox textBox = new TextBox(screen.getTerminalSize());
            textBox.setText(content);
            contentPanel.addComponent(textBox);
            window.setComponent(contentPanel);
            window.addWindowListener(new WindowListenerAdapter() {
                @Override
                public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
                    super.onInput(basePane, keyStroke, deliverEvent);
                    if (keyStroke.getKeyType() == KeyType.Escape) {
                        result[0] = textBox.getText();
                        window.close();
                    } else {
                        textBox.invalidate();
                    }
                }
            });
            textGUI.addWindowAndWait(window);
        }
        return result[0];
    }

    @SneakyThrows
    private void appendContentToFile(List<String> args) {
        System.out.print("Type the content to append. To finish, type '!wq'\n--> ");
        StringBuilder contentToAppend = new StringBuilder("\n");
        String content;
        while (!(content = scanner.nextLine()).equals("!wq")) {
            contentToAppend.append(content);
            System.out.print("--> ");
        }
        Files.write(Path.of(args.get(0)), contentToAppend.toString().getBytes(), StandardOpenOption.APPEND);
    }
}
