package ru.grigorii.NauJava.repository.old;

import java.util.List;

/**
 * Интерфейс репозитория для чтения всех сущностей
 * @param <T> Сущность
 */
public interface OldReadAllRepository<T>
{
    List<T> readAll();
}
