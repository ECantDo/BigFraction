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

    public BigFraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;

        this.updateNegative();
    }

    public BigFraction(BigFraction fraction) {
        this(fraction.numerator, fraction.denominator);
    }

    public BigFraction(double value) {
        // NaN
        if (Double.isNaN(value)) {
            this.numerator = 0;
            this.denominator = 0;
            return;
        }

        // INF
        if (Double.isInfinite(value)) {
            this.denominator = 0;
            if (value > 0) {
                this.numerator = 1;
            } else {
                this.numerator = -1;
            }
        }

        // Convert
        int size = (int) Math.pow(10, Double.toString(value).length() - 1);
        this.numerator = (int) (value * size);
        this.denominator = size;
        this.reduceFraction();
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

    /**
     * Divides this {@link BigFraction} by an integer
     *
     * @param value
     * @return
     */
    public BigFraction divide(int value) {
        BigFraction f = new BigFraction(this);
        f.denominator *= value;
        return f;
    }

    public BigFraction div(int value) {
        return this.divide(value);
    }

    /**
     * Divides {@link BigFraction}s, this one from another.
     *
     * @param fraction The {@link BigFraction} to divide.
     * @return Returns the resulting fraction.
     */
    public BigFraction divide(BigFraction fraction) {
        BigFraction f = new BigFraction(this);
        f.numerator *= fraction.denominator;
        f.denominator *= fraction.numerator;
        return f;
    }

    /**
     * Abbreviated name for the {@link #divide(BigFraction)} method.<br>
     * Divides {@link BigFraction}s, this one from another.
     *
     * @param fraction The {@link BigFraction} to divide.
     * @return Returns the resulting fraction.
     */
    public BigFraction div(BigFraction fraction) {
        return this.divide(fraction);
    }

    // Harder

    /**
     * Adds two {@link BigFraction} values from each other.  Gives the result in the lowest terms.
     * Unlike the subtract function, add does not have an abbreviated method for a shorter name.
     *
     * @param fraction The fraction to add.
     * @return Returns a new {@link BigFraction} of the resulting fraction.
     */
    public BigFraction add(BigFraction fraction) {
        BigFraction f = new BigFraction();
        f.denominator = this.denominator * fraction.denominator;
        f.numerator = this.numerator * fraction.denominator;
        f.numerator += this.denominator * fraction.numerator;
        return f.reduce();
    }

    /**
     * Subtracts two {@link BigFraction} values from each other.  Gives the result in the lowest terms.
     *
     * @param fraction The fraction to subtract.
     * @return Returns a new {@link BigFraction} of the resulting fraction.
     */
    public BigFraction subtract(BigFraction fraction) {
        BigFraction f = new BigFraction();
        f.denominator = this.denominator * fraction.denominator;
        f.numerator = this.numerator * fraction.denominator;
        f.numerator -= this.denominator * fraction.numerator;
        return f.reduce();
    }

    /**
     * Abbreviated method name for {@link #subtract(BigFraction)}.<br>
     * See the above method for more details -- subtracts two {@link BigFraction} values.
     *
     * @param fraction {@link BigFraction} to subtract.
     * @return Returns the resulting {@link BigFraction}.
     */
    public BigFraction sub(BigFraction fraction) {
        return this.subtract(fraction);
    }

    // Extras

    /**
     * Makes a new fraction with the numerator and denominator inverted.
     *
     * @return Returns a new {@link BigFraction} with the numerator and denominator swapped.
     */
    public BigFraction inverse() {
        BigFraction f = new BigFraction(this.denominator, this.numerator);
        return f;
    }

    /**
     * Makes the sign value of the numerator align with the overall sign of the fraction.
     * Does not return a new fraction, just updates the current one.
     */
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

        this.numerator = count == 1 ? -this.numerator : this.numerator;
    }

    /**
     * Gets the fraction in the lowest terms. Returns a new fraction.
     *
     * @return A new {@link BigFraction}, with the same value, in the lowest terms.
     * For example a fraction of 10/5 would be reduced to 2/1; 4/10 would reduce to 2/5.
     */
    public BigFraction reduce() {
        int gcf = greatestCommonFactor(this.numerator, this.denominator);
//        System.out.println(gcf);
        BigFraction f = new BigFraction(this);
        f.numerator /= gcf;
        f.denominator /= gcf;
        return f;
    }

    /**
     * Gets the fraction in the lowest terms.  Updates the values, for internal class use only.
     */
    private void reduceFraction() {
        int gcf = greatestCommonFactor(this.numerator, this.denominator);
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
    // Misc
    //==================================================================================================================
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof BigFraction)) {
            return false;
        }
        BigFraction fraction = new BigFraction((BigFraction) other).reduce();
        BigFraction copy = new BigFraction(this).reduce();

        return copy.numerator == fraction.numerator && copy.denominator == fraction.denominator;
    }


    //==================================================================================================================
    // Convert
    //==================================================================================================================

    @Override
    public String toString() {
        if (this.denominator == 0) {
            return "NaN";
        }
        BigFraction f = new BigFraction(this).reduce();
        f.updateNegative();
        String out = "";
//        out += this.negative ? "-" : "";
        out += f.numerator;
        if (f.denominator != 1) {
            out += "/" + f.denominator;
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
        BigFraction f1 = new BigFraction(-5, 2);
        System.out.println(f1);

        BigFraction f2 = new BigFraction(7, 4);
        System.out.println(f2);

        BigFraction a = f1.add(f2);
        BigFraction s = f1.sub(f2);
        BigFraction m = f1.mult(f2);
        BigFraction d = f1.div(f2);

        System.out.println("\nRESULTS:");
        System.out.println("+  " + a);
        System.out.println("-  " + s);
        System.out.println("*  " + m);
        System.out.println("/  " + d);

    }

}
