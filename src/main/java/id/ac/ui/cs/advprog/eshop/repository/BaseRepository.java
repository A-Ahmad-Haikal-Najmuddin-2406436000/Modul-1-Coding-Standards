package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.Iterator;

public interface BaseRepository<T, ID> {
    T create(T entity);
    T findById(ID id);
    Iterator<T> findAll();
    void delete(ID id);
    T edit(T entity);
}