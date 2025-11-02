package com.java.app.array.provider;

import com.java.app.array.comparator.StringArrayComparator;
import com.java.app.array.entity.string.StringArrayEntity;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public final class ComparatorArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(org.junit.jupiter.api.extension.ExtensionContext context) {
        StringArrayEntity id1 = new StringArrayEntity(1, "A", new String[] {"x"});
        StringArrayEntity id2 = new StringArrayEntity(2, "A", new String[] {"x"});
        StringArrayEntity id1Copy = new StringArrayEntity(1, "A", new String[] {"x"});
        StringArrayEntity nameA = new StringArrayEntity(3, "A", new String[] {"x"});
        StringArrayEntity nameB = new StringArrayEntity(4, "B", new String[] {"x"});
        StringArrayEntity nameACopy = new StringArrayEntity(5, "A", new String[] {"x"});
        StringArrayEntity first1 = new StringArrayEntity(6, "n1", new String[] {"a"});
        StringArrayEntity first2 = new StringArrayEntity(7, "n2", new String[] {"b"});
        StringArrayEntity len1 = new StringArrayEntity(9, "l1", new String[] {"x"});
        StringArrayEntity len3 = new StringArrayEntity(10, "l3", new String[] {"x","y","z"});
        StringArrayEntity len1Copy = new StringArrayEntity(11, "l11", new String[] {"z"});

        return Stream.of(
                Arguments.of(StringArrayComparator.ID, id1, id2, -1),
                Arguments.of(StringArrayComparator.ID, id1, id1Copy, 0),
                Arguments.of(StringArrayComparator.ID, id2, id1, 1),
                Arguments.of(StringArrayComparator.NAME, nameA, nameACopy, 0),
                Arguments.of(StringArrayComparator.NAME, nameA, nameB, -1),
                Arguments.of(StringArrayComparator.NAME, nameB, nameA, 1),
                Arguments.of(StringArrayComparator.FIRST, first1, first2, -1),
                Arguments.of(StringArrayComparator.FIRST, first1, first1, 0),
                Arguments.of(StringArrayComparator.FIRST, first2, first1, 1),
                Arguments.of(StringArrayComparator.LENGTH_ASC, len1, len1Copy, 0),
                Arguments.of(StringArrayComparator.LENGTH_ASC, len1, len3, -1),
                Arguments.of(StringArrayComparator.LENGTH_ASC, len3, len1, 1)
        );
    }
}
