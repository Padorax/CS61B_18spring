package byog.Core;

public class Position implements java.io.Serializable {
    private static final long serialVersionUID = 45498234798734234L;
    int x, y;

    public Position(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }
}
