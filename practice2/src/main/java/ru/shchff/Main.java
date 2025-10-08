package ru.shchff;

import ru.shchff.task1.Task1;
import ru.shchff.task2.Task2;
import ru.shchff.task3.Task3;
import ru.shchff.task4.Task4;
import ru.shchff.task5.Task5;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Щербаков Григорий, Вариант 1");

        System.out.println("Задача 1");
        Task1 task1 = new Task1();
        task1.solution(10);
        System.out.println();

        System.out.println("Задача 2");
        Task2 task2 = new Task2();
        task2.solution(10);
        System.out.println();

        System.out.println("Задача 3");
        Task3 task3 = new Task3();
        task3.solution();
        System.out.println();

        System.out.println("Задача 4");
        Task4 task4 = new Task4();
        task4.solution();
        System.out.println();

        System.out.println("Задача 5");
        Task5 task5 = new Task5();
        task5.solution(5);
        System.out.println();

    }
}