import synthesizer.GuitarString;

public class PianoHero {
    private static final int N = 37;

    public static void main(String[] args) {
        /* create an array of 37 GuitaString Objects */
        double CONCERT_A = 440.0;
        //set up the keyboard
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] strings = new GuitarString[N];
        for (int i = 0; i < N; i++) {
            double frequency = CONCERT_A * Math.pow(2, (i-24)/12);
            System.out.println(frequency);
            strings[i] = new GuitarString(frequency);
        }

        GuitarString string = strings[0];

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1) {
                    string = strings[index];
                    string.pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = string.sample();

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            string.tic();
        }
    }
}