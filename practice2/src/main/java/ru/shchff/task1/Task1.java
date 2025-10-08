package ru.shchff.task1;

import java.util.Random;

public class Task1
{
    public void solution(int n)
    {
        int[] arr = buildIntArrayWithRandomVals(n);
        int ans = findMaxAbsVal(arr);
        System.out.println(ans);
    }

    // Так как в заданиях не указан диапазон случайных чисел, использую свой [-50, 50]
    private int[] buildIntArrayWithRandomVals(int n)
    {
        int[] arr = new int[n];

        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            arr[i] = random.nextInt(101) - 50;
        }

        return arr;
    }

    private int findMaxAbsVal(int[] arr)
    {
        if (arr.length == 0)
        {
            throw new IllegalArgumentException("There is no max value in array with size 0");
        }

        int maxVal = Integer.MIN_VALUE;

        for (int val : arr)
        {
            if (Math.abs(val) > Math.abs(maxVal))
            {
                maxVal = val;
            }
        }

        return maxVal;
    }
}
