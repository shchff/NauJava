package ru.shchff.task5;

public class Task5
{
    public void solution(int seconds)
    {
        CountdownTimer timer = new CountdownTimer(seconds);

        timer.start();
        try
        {
            Thread.sleep(3000);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        timer.stop();
    }
}
