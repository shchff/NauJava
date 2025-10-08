package ru.shchff.task5;

public class CountdownTimer implements Task
{
    private final int seconds;

    private volatile boolean running = false;
    private Thread thread;

    public CountdownTimer(int seconds)
    {
        if (seconds < 0)
        {
            throw new IllegalArgumentException("Seconds must be >= 0");
        }
        this.seconds = seconds;
    }

    @Override
    public void start()
    {
        if (running)
        {
            System.out.println("Таймер уже запущен!");
            return;
        }

        running = true;

        thread = new Thread(() -> {
            try
            {
                int current = seconds;
                while (running && current > 0)
                {
                    System.out.println("Осталось: " + current + " сек.");
                    Thread.sleep(1000);
                    current--;
                }
            }
            catch (InterruptedException e)
            {
                System.out.println("Таймер был прерван");
            }
            finally
            {
                running = false;
            }
        });

        thread.start();
    }

    @Override
    public void stop()
    {
        if (running)
        {
            running = false;
            if (thread != null)
            {
                thread.interrupt();
            }
        }
        else
        {
            System.out.println("Таймер ещё не был запущен");
        }
    }
}

