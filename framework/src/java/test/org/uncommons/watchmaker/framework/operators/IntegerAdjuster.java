// ============================================================================
//   Copyright 2006, 2007 Daniel W. Dyer
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
import java.util.List;
import java.util.Random;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

/**
 * Trivial test operator that mutates all integers by adding a fixed offset.
 * @author Daniel Dyer
 */
@SuppressWarnings("unchecked")
final class IntegerAdjuster implements EvolutionaryOperator<Integer>
{
    private final int adjustment;

    public IntegerAdjuster(int adjustment)
    {
        this.adjustment = adjustment;
    }

    public <S extends Integer> List<S> apply(List<S> selectedCandidates, Random rng)
    {
        List<S> result = new ArrayList<S>(selectedCandidates.size());
        for (Integer i : selectedCandidates)
        {
            result.add((S) Integer.valueOf(i + adjustment));
        }
        return result;
    }
}
