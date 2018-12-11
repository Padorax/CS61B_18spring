public class LengthComparator implements NullSafeStringComparator {
    public int compare(String s1, String s2) {
        if ((s1 == null) && (s2 == null)) return 0;
        if (s1 == null) return -1
        if (s2 == null) return 1
        return s1.length() - s2.length();
    }

}