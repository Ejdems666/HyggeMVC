package hyggemvc.router;

/**
 * Created by adam on 21/02/2017.
 */
public class Notator {
    public static String toCamelCaseWithFirstUpperCase(String url){
        return toCamelCase(ucFirst(url));
    }

    public static String ucFirst(String url) {
        String withoutFirstLetter = url.substring(1, url.length());
        String firstLetter = url.substring(0, 1).toUpperCase();
        return firstLetter + withoutFirstLetter;
    }

    public static String toCamelCase(String url){
        StringBuilder cleanUrl = new StringBuilder();
        String newWordFirstLetter;
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '-' && i < url.length()-1) {
                newWordFirstLetter = url.substring(++i, i+1).toUpperCase();
                cleanUrl.append(newWordFirstLetter);
            } else {
                cleanUrl.append(url.charAt(i));
            }
        }
        return cleanUrl.toString();
    }
}
