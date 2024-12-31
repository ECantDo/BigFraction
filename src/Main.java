public class Main {
    public static void main(String[] args) {
        BigFraction frac = new BigFraction(-5, -2);
        System.out.println(frac.toString());

        frac = new BigFraction(5, 2);
        System.out.println(frac);

        frac = new BigFraction(-5, 2);
        System.out.println(frac);

        frac = new BigFraction(5, -2);
        System.out.println(frac);

    }
}