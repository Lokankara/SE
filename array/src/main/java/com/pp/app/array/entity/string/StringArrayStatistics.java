package com.pp.app.array.entity.string;

import com.pp.app.array.entity.ArrayEntity;
import com.pp.app.array.entity.ArrayStatistics;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class StringArrayStatistics extends ArrayStatistics<String> {
    private final int totalLength;
    private final String longest;
    private final String shortest;

    public StringArrayStatistics(ArrayEntity<String> entity) {
        super(entity);
        String[] arr = entity.getArray();
        this.totalLength = Arrays.stream(arr).mapToInt(s -> s != null ? s.length() : 0).sum();
        this.longest =
                Arrays.stream(arr).filter(Objects::nonNull).max(Comparator.comparingInt(String::length)).orElse("");
        this.shortest =
                Arrays.stream(arr).filter(Objects::nonNull).min(Comparator.comparingInt(String::length)).orElse("");
    }

    public int getTotalLength() {
        return totalLength;
    }

    public String getLongest() {
        return longest;
    }

    public String getShortest() {
        return shortest;
    }
}
