package models.GameLogic.utills;

public class StringUtils {
    public static String stringSeparator(String old) {
        String[] split = old.split("(?=\\p{Upper})");
        StringBuilder separated = new StringBuilder();
        for (String s : split) {
            separated.append(s).append(" ");
        }
        return separated.toString().trim();
    }
}
