package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface ReadService<T, ID>{
    T findById(ID id);
    List<T> findAll();
}
