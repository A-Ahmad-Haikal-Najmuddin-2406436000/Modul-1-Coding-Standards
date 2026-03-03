package id.ac.ui.cs.advprog.eshop.service;

public interface WriteService<T, ID> {
    T create(T entity);
    void edit(T entity);
    void delete(ID id);
}