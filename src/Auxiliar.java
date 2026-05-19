public class Auxiliar {

    public static int toInt(String s) {
        return s.isEmpty() ? 0 : Integer.parseInt(s);
    }

    public static double toDouble(String s) {
        return s.isEmpty() ? 0.0 : Double.parseDouble(s);
    }

    public static long toLong(String s) {
        return s.isEmpty() ? 0 : Long.parseLong(s);
    }
}