package com.java.app.array.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.java.app.array.entity.string.StringArrayEntity;
import com.java.app.array.provider.ComparatorArgumentsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class StringArrayComparatorTest {


    @ParameterizedTest(name = "Comparator {0} compares {1} vs {2} => expected sign {3}")
    @ArgumentsSource(ComparatorArgumentsProvider.class)
    @DisplayName("StringArrayComparator compare array in service")
    void comparatorProducesExpectedOrdering(StringArrayComparator comparator,
            StringArrayEntity left,
            StringArrayEntity right,
            int expectedSign) {
        int result = comparator.compare(left, right);
        int sign = Integer.compare(result, 0);
        int expected = Integer.compare(expectedSign, 0);
        assertEquals(expected, sign);
    }
}
