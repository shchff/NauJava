package ru.grigorii.NauJava.service.user;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Реализация генератора идентификаторов типа Long через инкремент, начинается с 1
 */
@Component
public class IdGeneratorWithIncrement implements IdGenerator
{
    private final AtomicLong currId = new AtomicLong();

    @Override
    public Long generateId()
    {
        return currId.incrementAndGet();
    }
}
