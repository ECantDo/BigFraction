import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BigFraction uses two integers to make a fraction
 */
public class BigFraction {

    public static final ArrayList<Integer> PRIMES = new ArrayList<>(List.of(2));

    int numerator, denominator;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public BigFraction() {
        this(0, 1);
    }

//    public BigFraction(int numerator, int denominator) {
//        this(numerator, denominator, false);
//    }

    public BigFraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("The denominator cannot be 0");
        }

        this.numerator = numerator;
        this.denominator = denominator;

        this.updateNegative();
    }

    public BigFraction(BigFraction fraction) {
        this(fraction.numerator, fraction.denominator);
    }

    //==================================================================================================================
    // Modify
    //==================================================================================================================
    // Easy functions
    public BigFraction multiply(int value) {
        BigFraction f = new BigFraction(this);
        f.numerator *= value;
        return f;
    }

    public BigFraction mult(int value) {
        return this.multiply(value);
    }

    public BigFraction multiply(BigFraction fraction) {
        BigFraction f = new BigFraction(this);
        f.numerator *= fraction.numerator;
        f.denominator *= fraction.denominator;
        return f;
    }

    public BigFraction mult(BigFraction fraction) {
        return this.multiply(fraction);
    }

    public BigFraction divide(int value) {
        BigFraction f = new BigFraction(this);
        f.denominator *= value;
        return f;
    }

    public BigFraction div(int value) {
        return this.divide(value);
    }

    public BigFraction divide(BigFraction fraction) {
        BigFraction f = new BigFraction(this);
        f.numerator *= fraction.denominator;
        f.denominator *= fraction.numerator;
        return f;
    }

    public BigFraction div(BigFraction fraction) {
        return this.divide(fraction);
    }

    // Harder
    public BigFraction add(BigFraction fraction) {
        BigFraction f = new BigFraction();
        f.denominator = this.denominator * fraction.denominator;
        f.numerator = this.numerator * fraction.denominator;
        f.numerator += this.denominator * fraction.numerator;
        f.reduce();
        return f;
    }

    public BigFraction subtract(BigFraction fraction) {
        BigFraction f = new BigFraction();
        f.denominator = this.denominator * fraction.denominator;
        f.numerator = this.numerator * fraction.denominator;
        f.numerator -= this.denominator * fraction.numerator;
        f.reduce();
        return f;
    }

    public BigFraction sub(BigFraction fraction) {
        return this.subtract(fraction);
    }


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

        this.numerator = count == 1? -this.numerator : this.numerator;
    }

    private void reduce() {
        int gcf = greatestCommonFactor(this.numerator, this.denominator);
//        System.out.println(gcf);
        this.numerator /= gcf;
        this.denominator /= gcf;
    }

    private static int greatestCommonFactor(int a, int b) {
        int[] numerator_factors = primeFactorize(a);
        int[] denominator_factors = primeFactorize(b);

        int gcf = 1;

        for (int i = 0; i < Math.min(numerator_factors.length, denominator_factors.length); i++) {
            gcf *= (int) Math.pow(PRIMES.get(i), Math.min(numerator_factors[i], denominator_factors[i]));
        }
        return gcf;
    }

    private static int[] primeFactorize(int number) {
        number = number < 0 ? -number : number;
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
            if (idx < PRIMES.size()) {
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
        String out = "";
//        out += this.negative ? "-" : "";
        out += this.numerator;
        if (this.denominator != 1) {
            out += "/" + this.denominator;
        }
        return out;
    }

    public double toDouble() {
        if (this.denominator == 0) {
            return Double.NaN;
        }

        return this.numerator / (double) this.denominator;
    }

    public float toFloat() {
        if (this.denominator == 0) {
            return Float.NaN;
        }

        return this.numerator / (float) this.denominator;
    }

    public static void main(String[] args) {
//        System.out.println("Hello, world!");
        BigFraction f1 = new BigFraction(-5, 2);
        System.out.println(f1);

        BigFraction f2 = new BigFraction(7, 4);
        System.out.println(f2);

        System.out.println("\nRESULTS:");
        System.out.println("+  " + f1.add(f2));
        System.out.println("-  " + f1.sub(f2));
        System.out.println("*  " + f1.mult(f2));
        System.out.println("/  " + f1.div(f2));
    }

}
