package ru.grigorii.NauJava.repository;

import java.util.List;

/**
 * Интерфейс репозитория для чтения всех сущностей
 * @param <T> Сущность
 */
public interface ReadAllRepository<T>
{
    List<T> readAll();
}
