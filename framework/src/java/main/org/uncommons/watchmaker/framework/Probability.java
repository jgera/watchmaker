// ============================================================================
//   Copyright 2006-2009 Daniel W. Dyer
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// ============================================================================
package org.uncommons.watchmaker.framework;

import java.util.Random;

/**
 * Immutable value type for probabilities.  Forces numeric probabilities to be within the
 * range 0..1 inclusive and provides useful utility methods for working with
 * probabilities (such as generating an event with a given probability).
 * @author Daniel Dyer
 */
public final class Probability extends Number implements Comparable<Probability>
{
    /**
     * Convenient constant representing a probability of zero.  If an event has
     * a probability of zero it will never happen (it is an impossibility).
     * @see #ONE
     * @see #EVENS
     */
    public static final Probability ZERO = new Probability(0);

    /**
     * Convenient constant representing a probability of 0.5 (used to model
     * an event that has a 50/50 chance of occurring).
     * @see #ZERO
     * @see #ONE
     */
    public static final Probability EVENS = new Probability(0.5d);

    /**
     * Convenient constant representing a probability of one.  An event with
     * a probability of one is a certainty.
     * @see #ZERO
     * @see #EVENS
     */
    public static final Probability ONE = new Probability(1);

    private final double probability;

    /**
     * @param probability The probability value (a number in the range 0..1 inclusive).  A
     * value of zero means that an event is guaranteed not to happen.  A value of 1 means
     * it is guaranteed to occur.
     */
    public Probability(double probability)
    {
        if (probability < 0 || probability > 1)
        {
            throw new IllegalArgumentException("Probability must be in the range 0..1 inclusive.");
        }
        this.probability = probability;
    }


    /**
     * Generates an event according the probability value {@literal p}.
     * @param rng A source of randomness for generating events.
     * @return True with a probability of {@literal p}, fales with a probability of
     * {@literal 1 - p}.
     */
    public boolean nextEvent(Random rng)
    {
        return rng.nextDouble() < probability;        
    }


    /**
     * The complement of a probability p is 1 - p.  If p = 0.75, then the complement is 0.25.
     * @return The complement of this probability.
     */
    public Probability getComplement()
    {
        return new Probability(1 - probability);
    }


    /**
     * Converting a fractional probability into an integer is not meaningful since
     * all useful information is discarded.  For this reason, this method is over-ridden
     * to thrown an {@link ArithmeticException}, except when the probability is exactly
     * zero or one.
     * @throws ArithmeticException Unless the probability is exactly zero or one.
     * @return An integer probability.
     */
    public int intValue()
    {
        if (probability % 1 != 0)
        {
            throw new ArithmeticException("Cannot convert probability to integer due to loss of precision.");
        }
        else
        {
            return (int) probability;
        }
    }


    /**
     * Converting a fractional probability into an integer is not meaningful since
     * all useful information is discarded.  For this reason, this method is over-ridden
     * to thrown an {@link ArithmeticException}, except when the probability is exactly
     * zero or one.
     * @throws ArithmeticException Unless the probability is exactly zero or one.
     * @return An integer probability.
     */
    public long longValue()
    {
        return intValue();
    }


    /**
     * Returns the probability value as a float.
     * @return A real number between 0 and 1 inclusive.
     */
    public float floatValue()
    {
        return (float) probability;
    }


    /**
     * Returns the probability value as a double.
     * @return A real number between 0 and 1 inclusive.
     */
    public double doubleValue()
    {
        return probability;
    }


    /**
     * Determines whether this probability value is equal to some other object.
     * To be considered equal the other object must also be a Probability object
     * with an indentical probability value.
     * @param o The object to compare against.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Probability that = (Probability) o;

        return Double.compare(that.probability, probability) == 0;
    }


    /**
     * Over-ridden to be consistent with {@link #equals(Object)}. 
     * @return The hash code value.
     */
    @Override
    public int hashCode()
    {
        long temp = probability != +0.0d ? Double.doubleToLongBits(probability) : 0L;
        return (int) (temp ^ (temp >>> 32));
    }


    /**
     * Compares this object with the specified object for order. Returns a negative
     * integer, zero, or a positive integer as this object is less than, equal to, or
     * greater than the specified object.
     * @param other Another Probability value.
     * @return A negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    public int compareTo(Probability other)
    {
        return Double.compare(this.probability, other.doubleValue());
    }


    /**
     * @return A string representation of the probability value (a number between
     * 0 and 1 inclusive).
     */
    @Override
    public String toString()
    {
        return String.valueOf(probability);
    }
}
