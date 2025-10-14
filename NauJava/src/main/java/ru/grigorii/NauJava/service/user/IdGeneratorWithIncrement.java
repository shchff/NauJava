package ru.grigorii.NauJava.service.user;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

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
