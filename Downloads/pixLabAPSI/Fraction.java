/*
 * Fraction class
 * @author  Richard Kick
 * @version September, 2002; last updated October 2002
 */
import java.lang.ArithmeticException;

public class Fraction
{
  // Instance Fields
  private int num;
  private int denom;

  /*
   * Default Fraction Constructor that creates the fraction 0 / 1
   */
  public Fraction( ) // default
  {
    num = 0;
    denom = 1;
  }

  /*
   * Fraction Constructor that creates the fraction num / 1
   * @param num    numerator of the fraction to be constructed; demonimator is 1
   */
  public Fraction( int num ) // denom = 1
  {
    this.num = num;
    this.denom = 1;
  }

  /*
   * Fraction Constructor that creates the fraction num / denom
   * @param num    numerator of the fraction to be constructed
   * @param denom  denominator of the fraction to be constructed
   * @throws       ArithmeticException if denom is 0
   */
  public Fraction( int num, int denom )
  {
    if (denom == 0)
    {
      throw new ArithmeticException("Error: denominator is 0");
    }
    this.num = num;
    this.denom = denom;
  }

  /*
   * Private helper method calculates the greatest common divisor of a and b
   * Precondition:  a is not 0 and b is not 0
   * @param a   integer whose divisors are used to find the greatest common divisor with b
   * @param b   integer whose divisors are used to find the greatest common divisor with a
   * @return    the greatest common divisor of a and b
   */
  private int gcd(int a, int b)
  {
    int rem = a % b;
    if (rem == 0) return b;
    return gcd(b, rem);
  }

  /*
   * calculates and returns the sum of the current Fraction and the parameter rhs
   * Precondition:  rhs is not null
   * @param rhs   Fraction to be added to the current Fraction
   * @return      the sum of the current Fraction and the parameter rhs
   */
  public Fraction add(Fraction rhs)
  {
    int num = this.num * rhs.denominator() + this.denom * rhs.numerator();
    int denom = this.denom * rhs.denominator();
    return new Fraction(num, denom);
  }

  /*
   * calculates and returns the quotient of the current Fraction and the parameter rhs
   * Precondition:  rhs is not null
   * @param rhs   Fraction to be divided into the current Fraction
   * @return      the quotient of the current Fraction and the parameter rhs
   */
  public Fraction divide(Fraction rhs)
  {
    if (rhs.numerator() == 0)
    {
      throw new ArithmeticException("Error: Attempt to divide by 0");
    }
    int num = this.num * rhs.denominator();
    int denom = this.denom * rhs.numerator();
    Fraction temp = new Fraction(num, denom);
    return temp;
  }

  /*
   * provides access to the numerator of the current Fraction
   * @return      the numerator of the current Fraction
   */
  public int numerator( )
  {
    return num;
  }

  /*
   * provides access to the denominator of the current Fraction
   * @return      the denominator of the current Fraction
   */
  public int denominator( )
  {
    return denom;
  }

  /*
   * provides access to the denominator of the current Fraction
   * @return      the denominator of the current Fraction
   */
  public double decimal( )
  {
    return  (double) num * (1.0 / denom);
  }

  /*
   * provides access to the String form of the current Fraction
   * @return      the String form of the current Fraction
   */
  public String toString()
  {
    return "(" + num + " / " + denom + ")";
  }

  /*
   * provides access to the reduced form of the current Fraction;
   * the greatest common divisor of the numberator and the denominator is divided
   * out of both the numerator and denominator; the denominator of the reduced form
   * is always positive
   * @return      the Fraction that is the reduced equivilant of the current Fraction
   */
  public Fraction reduce()
  {
      int div = gcd(num, denom);
      int denom = this.denom / div;
      int num = this.num / div;
      if (denom < 0)
      {
        num = -num;
        denom = -denom;
      }
      return new Fraction(num, denom);
  }
}
