package com.java.app.array.service;


import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.specification.AverageGreaterThanSpecification;
import com.java.app.array.specification.IdSpecification;
import com.java.app.array.specification.MaxGreaterThanSpecification;
import com.java.app.array.specification.NameSpecification;
import com.java.app.array.specification.SumGreaterThanSpecification;
import com.java.app.array.specification.SumLessThanSpecification;

import java.util.List;

public class SearchService {
    private final ArrayService arrayService;

    public SearchService() {
        this.arrayService = new ArrayService();
    }

    public List<ArrayEntity> findById(int id) {
        return arrayService.searchArrays(new IdSpecification(id));
    }

    public List<ArrayEntity> findByName(String name) {
        return arrayService.searchArrays(new NameSpecification(name));
    }

    public List<ArrayEntity> findBySumGreaterThan(int threshold) {
        return arrayService.searchArrays(new SumGreaterThanSpecification(threshold));
    }

    public List<ArrayEntity> findBySumLessThan(int threshold) {
        return arrayService.searchArrays(new SumLessThanSpecification(threshold));
    }

    public List<ArrayEntity> findByAverageGreaterThan(double threshold) {
        return arrayService.searchArrays(new AverageGreaterThanSpecification(threshold));
    }

    public List<ArrayEntity> findByMaxGreaterThan(int threshold) {
        return arrayService.searchArrays(new MaxGreaterThanSpecification(threshold));
    }
}
