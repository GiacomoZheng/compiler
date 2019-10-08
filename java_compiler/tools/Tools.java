package tools;

public class Tools {
    public static String fixEscape(Character c) {
        switch (c) {
            case '\b': return "\\b";
            case '\t': return "\\t";
            case '\n': return "\\n";
            case '\f': return "\\f";
            case '\r': return "\\r";
            default: return c.toString();
        }
    }
    public static String fixEscape(String string) {
        String res = "";
        for (int j = 0; j < string.length(); j++) {
            res += fixEscape(string.charAt(j));
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(fixEscape("asas\tfdff"));
    }
}