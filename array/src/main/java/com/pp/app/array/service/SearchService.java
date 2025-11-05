package com.pp.app.array.service;

import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.specification.AverageGreaterThanSpecification;
import com.pp.app.array.specification.IdSpecification;
import com.pp.app.array.specification.MaxGreaterThanSpecification;
import com.pp.app.array.specification.NameSpecification;
import com.pp.app.array.specification.SumGreaterThanSpecification;
import com.pp.app.array.specification.SumLessThanSpecification;

import java.util.List;

public class SearchService {
    private final ArrayService arrayService;

    public SearchService() {
        this.arrayService = new ArrayService();
    }

    public List<IntArrayEntity> findById(int id) {
        return arrayService.searchArrays(new IdSpecification(id));
    }

    public List<IntArrayEntity> findByName(String name) {
        return arrayService.searchArrays(new NameSpecification(name));
    }

    public List<IntArrayEntity> findBySumGreaterThan(int threshold) {
        return arrayService.searchArrays(new SumGreaterThanSpecification(threshold));
    }

    public List<IntArrayEntity> findBySumLessThan(int threshold) {
        return arrayService.searchArrays(new SumLessThanSpecification(threshold));
    }

    public List<IntArrayEntity> findByAverageGreaterThan(double threshold) {
        return arrayService.searchArrays(new AverageGreaterThanSpecification(threshold));
    }

    public List<IntArrayEntity> findByMaxGreaterThan(int threshold) {
        return arrayService.searchArrays(new MaxGreaterThanSpecification(threshold));
    }
}
