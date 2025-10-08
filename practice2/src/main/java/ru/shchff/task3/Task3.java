package ru.shchff.task3;

import java.util.ArrayList;
import java.util.List;

public class Task3
{
    public void solution()
    {
        List<Employee> list = new ArrayList<>();

        list.add(new Employee("Щербаков Г.A.", 21, "Naumen",90_000.0));
        list.add(new Employee("Кабылов К.Ф.", 35, "Naumen",90_000.0));
        list.add(new Employee("Басараб Д.А.", 19, "Naumen",90_000.0));
        list.add(new Employee("Марченко Д.С.", 45, "Naumen",90_000.0));
        list.add(new Employee("Попов С.Д.", 64, "Naumen",90_000.0));

        list.stream()
                .filter(e -> e.getAge() > 30)
                .forEach(System.out::println);
    }
}
