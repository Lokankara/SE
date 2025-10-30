package com.java.app.array.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T entity);
}