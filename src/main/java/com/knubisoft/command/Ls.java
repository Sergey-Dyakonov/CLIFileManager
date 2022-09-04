package com.knubisoft.command;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Ls extends Command {

    private static final String DELIMETER = " ";

    public Ls(Context context) {
        super(context);
    }

    @Override
    @SneakyThrows
    public String execute(List<String> args) {
        //TODO : format output
        File curDir = context.getCurrentDir();
        File[] allFiles = curDir.listFiles();
        List<String> filesInfo = new ArrayList<>();
        if (allFiles != null) {
            for (File file : allFiles) {
                filesInfo.add(formFileInfo(file));
            }
            return formatStrings(filesInfo, "NAME" + DELIMETER + "AVAILABLE_SPACE" + DELIMETER + "SIZE" + DELIMETER + "READABLE" + DELIMETER + "WRITABLE" + DELIMETER + "EXTENSION");
        }
        return "";
    }

    private String formatStrings(List<String> filesInfo, String headerString) {
        List<Integer> sizes = new ArrayList<>();
        filesInfo.set(0, headerString);
        filesInfo.forEach(file -> sizes.add(0));
        getMaxColsSizes(filesInfo, sizes);
        reformatStrings(filesInfo, sizes);
        StringBuilder res = new StringBuilder();
        for (String s : filesInfo) {
            res.append(s).append("\n");
        }
        return res.toString();
    }

    private void reformatStrings(List<String> filesInfo, List<Integer> sizes) {
        StringBuilder res;
        for (int a = 0; a < filesInfo.size(); a++) {
            res = new StringBuilder();
            String[] properties = filesInfo.get(a).split(DELIMETER);
            for (int i = 0; i < properties.length; i++) {
                res.append(String.format("%-" + sizes.get(i) + "s\t", properties[i]));
            }
            filesInfo.set(a, res.toString());
        }
    }

    private void getMaxColsSizes(List<String> filesInfo, List<Integer> sizes) {
        for (String s : filesInfo) {
            String[] properties = s.split(DELIMETER);
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].length() > sizes.get(i)) {
                    sizes.set(i, properties[i].length());
                }
            }
        }
    }

    @SneakyThrows
    private String formFileInfo(File file) {
        return file.getName() + " " +
                file.getUsableSpace() + " " +
                Files.size(Path.of(file.getPath())) + " " +
                Files.isReadable(Path.of(file.getPath())) + " " +
                Files.isWritable(Path.of(file.getPath())) + " " +
                FilenameUtils.getExtension(file.getName());
    }
}
