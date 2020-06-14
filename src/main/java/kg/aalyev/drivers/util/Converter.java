package kg.aalyev.drivers.util;

/**
 * @author Kuttubek Aidaraliev
 */

public class Converter {

    public static Integer strToInt(String value) {
        if (value == null) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long strToLong(String value) {
        if (value == null) return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
