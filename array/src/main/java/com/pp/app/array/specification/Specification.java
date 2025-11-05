package com.pp.app.array.specification;

public interface Specification<T> {

    boolean specify(T entity);
}