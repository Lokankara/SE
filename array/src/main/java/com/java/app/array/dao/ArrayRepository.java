package com.java.app.array.dao;

import com.java.app.array.comparator.ArrayComparator;
import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.specification.Specification;

import java.util.List;
import java.util.function.Predicate;

public interface ArrayRepository<T> {

    void add(T array);

    void remove(T array);

    void removeById(int id);

    List<T> findBySpecification(Specification<T> spec);

    List<T> findAll();

    List<T> sortBy(ArrayComparator comparator);

    void clear();

    List<ArrayEntity> queryStream(Specification<ArrayEntity> specification);

    List<ArrayEntity> queryPredicate(Predicate<ArrayEntity> predicate);
}
