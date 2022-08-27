package cc.kocho.utils;

public class JsonUtils {
    public static boolean check(String text){
        if (text.startsWith("{") && text.endsWith("}")) {
            return true;
        } else if (text.startsWith("[") && text.endsWith("]")) {
            return true;
        }
        return false;
    }
}
