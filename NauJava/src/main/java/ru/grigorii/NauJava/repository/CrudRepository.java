package ru.grigorii.NauJava.repository;

/**
 * Интерфейс репозитория с CRUD операциями
 * @param <T> Сущность
 * @param <ID> Идентификатор сущности
 */
public interface CrudRepository<T, ID>
{
    void create(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
}
