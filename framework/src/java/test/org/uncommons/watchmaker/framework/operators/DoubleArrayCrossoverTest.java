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
package org.uncommons.watchmaker.framework.operators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.testng.annotations.Test;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.Probability;

/**
 * Unit test for cross-over with floating point arrays.
 * @author Daniel Dyer
 */
public class DoubleArrayCrossoverTest
{
    private final Random rng = new MersenneTwisterRNG();

    @Test
    public void testCrossover()
    {
        EvolutionaryOperator<double[]> crossover = new DoubleArrayCrossover(new ConstantGenerator<Integer>(1),
                                                                            Probability.ONE);
        List<double[]> population = new ArrayList<double[]>(4);
        population.add(new double[]{1.1d, 2.2d, 3.3d, 4.4d, 5.5d});
        population.add(new double[]{6.6d, 7.7d, 8.8d, 9.9d, 10});
        population.add(new double[]{11, 12, 13, 14, 15});
        population.add(new double[]{16, 17, 18, 19, 20});
        Set<Double> values = new HashSet<Double>(20);
        for (int i = 0; i < 20; i++)
        {
            population = crossover.apply(population, rng);
            assert population.size() == 4 : "Population size changed after cross-over.";
            for (double[] individual : population)
            {
                assert individual.length == 5 : "Invalid candidate length: " + individual.length;
                for (double value : individual)
                {
                    values.add(value);
                }
            }
            // All of the individual elements should still be present, just jumbled up
            // between individuals.
            assert values.size() == 20 : "Information lost during cross-over.";
            values.clear();
        }
    }


    /**
     * The {@link DoubleArrayCrossover} operator is only defined to work on populations
     * containing arrays of equal lengths.  Any attempt to apply the operation to
     * populations that contain different length arrays should throw an exception.
     * Not throwing an exception should be considered a bug since it could lead to
     * hard to trace bugs elsewhere.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDifferentLengthParents()
    {
        EvolutionaryOperator<double[]> crossover = new DoubleArrayCrossover(1, Probability.ONE);
        List<double[]> population = new ArrayList<double[]>(2);
        population.add(new double[]{1.1d, 2.2d, 3.3d, 4.4d, 5.5d});
        population.add(new double[]{6.6d, 7.7d, 8.8d});
        // This should cause an exception since the parents are different lengths.
        crossover.apply(population, rng);
    }
}
