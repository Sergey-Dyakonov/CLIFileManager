package com.knubisoft.command;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Ls extends Command{

    public Ls(Context context) {
        super(context);
    }

    @Override
    @SneakyThrows
    public String execute(List<String> args) {
        //TODO : format output
        File curDir = context.getCurrentDir();
        File[] allFiles = curDir.listFiles();
        StringBuilder res = new StringBuilder();
        res.append("FILE NAME\tAVAILABLE BYTES\tSIZE\tREADABLE\tWRITABLE\tEXTENSION");
        if (allFiles != null) {
            /*formattedOutput(List.of(allFiles),
                    File::getName,
                    File::getUsableSpace,
                    Files::size,
                    Files::isReadable,
                    Files::isWritable,
                    FilenameUtils::getExtension);*/
            for (File file : allFiles) {
                res.append(file.getName()).append(":").append(file.getUsableSpace()).append("\t");
                res.append(Files.size(Path.of(file.getPath()))).append("\t");
                if(Files.isReadable(Path.of(file.getPath()))) {
                    res.append(" + ");
                } else {
                    res.append(" - ");
                }
                res.append("\t");
                if(Files.isWritable(Path.of(file.getPath()))) {
                    res.append(" + ");
                } else {
                    res.append(" - ");
                }
                res.append("\t");
                res.append(FilenameUtils.getExtension(file.getName())).append("\n");
            }
        }
        return res.toString();
    }

    private String formattedOutput(List<File> files, Function<File, Object>... properties){
        int maxLength;
        StringBuilder builder = new StringBuilder();
        Map<File, String> fileToInfoStr = new LinkedHashMap<>();
        files.forEach(file -> fileToInfoStr.put(file, ""));
        for (Function<File, Object> getProperty : properties) {
            maxLength = findLongestProperty(files, getProperty);
            int finalMaxLength = maxLength;
            files.forEach(file -> fileToInfoStr.put(file, fileToInfoStr.get(file) + "%-" + finalMaxLength + "s\t"));
        }
        return builder.toString();
    }

    private int findLongestProperty(List<File> files, Function<File, Object> getProperty) {
        int maxLength = 0;
        for (File file : files) {
            String res = getProperty.apply(file).toString();
            if(res.length() > maxLength){
                maxLength = res.length();
            }
        }
        return maxLength;
    }
}
