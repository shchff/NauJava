package ru.grigorii.NauJava.repository;

import java.util.Collection;

public interface CrudRepository<T, ID>
{
    void create(T entity);
    T read(ID id);
    Collection<T> readAll();
    void update(T entity);
    void delete(ID id);
}
