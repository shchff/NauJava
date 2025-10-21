package ru.grigorii.NauJava.repository.old;

/**
 * Интерфейс репозитория с CRUD операциями
 * @param <T> Сущность
 * @param <ID> Идентификатор сущности
 */
public interface OldCrudRepository<T, ID>
{
    void create(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
}
