/**
 * BigFraction uses two integers to make a fraction
 */
public class BigFraction {

    int numerator, denominator;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public BigFraction() {
        this(0, 1);
    }

    public BigFraction(int num, int denom) {
        this.numerator = num;
        this.denominator = denom;
    }

    //==================================================================================================================
    // Modify
    //==================================================================================================================

    //==================================================================================================================
    // Convert
    //==================================================================================================================

    @Override
    public String toString(){
        String out = "" + this.numerator;
        if (this.denominator != 1) {
            out += "/" + this.denominator;
        }
        return out;
    }

    public double toFloat(){
        return this.numerator/(double)this.denominator;
    }

}
