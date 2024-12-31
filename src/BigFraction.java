import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BigFraction uses two integers to make a fraction
 */
public class BigFraction {

    public static final ArrayList<Integer> PRIMES = new ArrayList<>(List.of(2));

    int numerator, denominator;
    boolean negative;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public BigFraction() {
        this(0, 1);
    }

    public BigFraction(int num, int denom) {
        this(num, denom, false);
    }

    public BigFraction(int num, int denom, boolean neg) {
        if (denom == 0) {
            throw new IllegalArgumentException("The denominator cannot be 0");
        }

        this.numerator = num;
        this.denominator = denom;
        this.negative = neg;
    }

    public BigFraction(BigFraction fraction) {
        this(fraction.numerator, fraction.denominator, fraction.negative);
    }

    //==================================================================================================================
    // Modify
    //==================================================================================================================
    // Easy functions
    public void multiply(int value) {
        this.numerator *= value;
    }

    public void mult(int value) {
        this.multiply(value);
    }

    public void multiply(BigFraction fraction) {
        this.numerator *= fraction.numerator;
        this.denominator *= fraction.denominator;
    }

    public void mult(BigFraction fraction) {
        this.multiply(fraction);
    }

    public void divide(int value) {
        this.denominator *= value;
    }

    public void div(int value) {
        this.divide(value);
    }

    public void divide(BigFraction fraction) {
        this.numerator *= fraction.denominator;
        this.denominator *= fraction.numerator;
    }

    public void div(BigFraction fraction) {
        this.divide(fraction);
    }
    // Harder


    // Extras
    public void inverse() {
        int temp = this.numerator;
        this.numerator = this.denominator;
        this.denominator = temp;
    }

    public void updateNegative() {
        int count = 0;
        if (this.numerator < 0) {
            this.numerator = -this.numerator;
            count++;
        }
        if (this.denominator < 0) {
            this.denominator = -this.denominator;
            count++;
        }

        this.negative = this.negative ^ count == 1;
    }

    private void reduce() {

    }

    private static int[] primeFactorize(int number) {
        int prime = 2;
        ArrayList<Integer[]> primes = new ArrayList<>();
        int idx = 0;
        while (number > 1) {
            double half;
            Integer[] primeCounter = new Integer[]{0, prime};
            primes.add(primeCounter);
            do {
                half = number / (double) prime;
                if (half % 1 != 0) {
                    break;
                }
                number = (int) half;
                primeCounter[0]++;
            } while (true);
            idx++;
            // Get next prime
            if (idx < PRIMES.size()){
                prime = PRIMES.get(idx);
            } else {
                prime = getNextPrime();
            }
        }
        int[] primeOut = new int[primes.size()];
        idx = 0;
        for (Integer[] i : primes) {
            primeOut[idx++] = i[0];
        }
        return primeOut;
    }

    private static Integer getNextPrime() {
        // Get next prime
        int prime = PRIMES.getLast();
        boolean foundNewPrime;
        do {
            foundNewPrime = true;
            prime++;
            // Check all previous primes for existing prime
            for (Integer i : PRIMES) {
                if (prime % i == 0) {
                    foundNewPrime = false;
                    break;
                }
            }
        } while (!foundNewPrime);
        PRIMES.add(prime);

        return prime;
    }


    //==================================================================================================================
    // Convert
    //==================================================================================================================

    @Override
    public String toString() {
        if (this.denominator == 0) {
            return "NaN";
        }
        this.reduce();
        this.updateNegative();
        String out = this.negative ? "-" : "";
        out += this.numerator;
        if (this.denominator != 1) {
            out += "/" + this.denominator;
        }
        return out;
    }

    public double toFloat() {
        if (this.denominator == 0) {
            return Double.NaN;
        }

        return this.numerator / (double) this.denominator;
    }

    public static void main(String[] args) {
        int[] facts = BigFraction.primeFactorize(6);

        System.out.println(Arrays.toString(facts));
        for (int i = 0; i < 32; i++){
            System.out.println(i < PRIMES.size() ? PRIMES.get(i) : getNextPrime());
        }
    }

}
