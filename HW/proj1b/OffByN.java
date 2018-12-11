public class OffByN implements CharacterComparator{

    private static int N;

    public OffByN(int n) {
        N = n;//how about no class/instance variable?
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == N) {
            return true;
        }
        return false;
    }
}
