package cc.kocho.utils;

import cc.kocho.Erythrina;
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
    public static File getImageFile(String indexed, String quality){
        File file;
        String tmp_after = indexed + "." + Erythrina.getConfig().getImageFile().getFormat();
        switch (quality){
            case "low":
                file = new File(Erythrina.getConfig().getImageFile().getDirectory().getLow() + tmp_after);
                break;
            case "medium":
                file = new File(Erythrina.getConfig().getImageFile().getDirectory().getMedium() + tmp_after);
                break;
            case "high":
                file = new File(Erythrina.getConfig().getImageFile().getDirectory().getHigh() + tmp_after);
                break;
            default:
                return null;
        }
        return file;
    }
}
