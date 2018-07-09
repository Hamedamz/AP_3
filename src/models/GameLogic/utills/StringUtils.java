package models.GameLogic.utills;

public class StringUtils {
    public static String stringSeparator(String old) {
        String[] split = old.split("(?=\\p{Upper})");
        String seperated = "";
        for (String s1 : split) {
            seperated += s1 + " ";
        }
        return seperated.trim();
    }
}
