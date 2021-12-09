public class Research{
    public static void main(String[] args) {

    }

    public static void backwards(int i) {
        int power = 1;
        for (int j = 0; j < i; j++) {
            power *= 2;
        }
        int[] keys = new int[1000*power];

        for (int j = 0; j < keys.length; j++) {
            keys[j] = keys.length-1-j;
        }
    }

    public static void random(int i) {

    }
}
