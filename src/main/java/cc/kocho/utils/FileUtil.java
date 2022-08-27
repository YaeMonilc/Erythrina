package cc.kocho.utils;

import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static String readFile(File file){
        final String[] text = {""};
        new FileReader(file, StandardCharsets.UTF_8).readLines((LineHandler) line -> text[0] += line);
        return text[0];
    }
    public static void writeFile(File file, String text){
        new FileWriter(file, StandardCharsets.UTF_8).write(text);
    }
}
