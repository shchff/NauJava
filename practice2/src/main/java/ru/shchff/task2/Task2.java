package ru.shchff.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task2
{
    public void solution(int n)
    {
        List<Double> list = buildDoubleListWithRandomVals(n);
        System.out.println(list);
        List<Double> sorted = mergeSort(list);
        System.out.println(sorted);
    }

    // Так как в заданиях не указан диапазон случайных чисел, использую свой [-50.0, 50.0]
    private List<Double> buildDoubleListWithRandomVals(int n)
    {
        List<Double> list = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            list.add(random.nextDouble() * 100 - 50);
        }

        return list;
    }

    private List<Double> mergeSort(List<Double> list)
    {
        if (list.size() <= 1)
        {
            return list;
        }

        int middle = list.size() / 2;

        List<Double> left = new ArrayList<>(list.subList(0, middle));
        List<Double> right = new ArrayList<>(list.subList(middle, list.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static List<Double> merge(List<Double> left, List<Double> right)
    {
        List<Double> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size())
        {
            if (left.get(i) <= right.get(j))
            {
                result.add(left.get(i));
                i++;
            }
            else
            {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size())
        {
            result.add(left.get(i++));
        }

        while (j < right.size())
        {
            result.add(right.get(j++));
        }

        return result;
    }
}
