package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        //String s = game.generateRandomString(5);
        //game.drawFrame(s);
        //game.flashSequence(s);
        //String s = game.solicitNCharsInput(5);
        //System.out.println(s);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.ORANGE);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String res = "";
        for (int i = 0; i < n; i++) {
            int randIndex = rand.nextInt(26);
            res += CHARACTERS[randIndex];
        }
        return res;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.clear(Color.ORANGE);
        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.005);
        StdDraw.text(width/2, height/2, s);
        //TODO: If game is not over, display relevant game information at the top of the screen
        Font topfont = new Font("Monaco",Font.PLAIN, 25);
        StdDraw.setFont(topfont);
        StdDraw.line(0,38,40, 38);
        String leftText = "Round: " + Integer.toString(this.round);
        StdDraw.textLeft(1, 39, leftText);

        int encourageIndex = rand.nextInt(ENCOURAGEMENT.length);
        StdDraw.textRight(39,39, ENCOURAGEMENT[encourageIndex]);

        if (playerTurn) {
            StdDraw.text(width/2, height - 1, "Type!");
        }
        else {
            StdDraw.text(width/2, height-1, "Watch!" );
        }

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        //for (char c : letters.toCharArray()) {
        for(int i = 0, n = letters.length(); i < n; i++){
            char c = letters.charAt(i);
            String s = String.valueOf(c);
            drawFrame(s);
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        int keyNum = 0;
        String charsInput = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                keyNum += 1;
                charsInput += StdDraw.nextKeyTyped();
                drawFrame(charsInput);
                if (keyNum == n) {
                    StdDraw.pause(500);
                    return charsInput;
                }
            }
        }
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        //TODO: Establish Game loop
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round: "+ Integer.toString(round));
            StdDraw.pause(1500);
            String targetString = generateRandomString(round);
            flashSequence(targetString);
            playerTurn = true;
            String inputString = solicitNCharsInput(round);
            if (inputString.equals(targetString)) {
                round += 1;
            } else {
                gameOver = true;
                String endSign = "Game Over! You made it to round: " + Integer.toString(round);
                drawFrame(endSign);
            }
        }

    }

}
