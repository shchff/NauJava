package ru.grigorii.NauJava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.grigorii.NauJava.console.CommandProcessor;
import java.util.Scanner;

/**
 * Конфигурация CommandLineRunner для через CLI
 */
@Configuration
public class CommandProcessorConfig
{
    @Autowired
    private CommandProcessor commandProcessor;

    @Autowired
    private ConfigurableApplicationContext context;

//    @Bean
//    public CommandLineRunner commandScanner()
//    {
//        return args ->
//        {
//            try (Scanner scanner = new Scanner(System.in))
//            {
//                System.out.println("Please enter a command.\n'help' for checking the usage\n'exit' for exit the program");
//                while (true)
//                {
//                    System.out.print("> ");
//                    String input = scanner.nextLine();
//
//                    if ("exit".equalsIgnoreCase(input.trim()))
//                    {
//                        System.out.println("Exiting program...");
//                        int exitCode = SpringApplication.exit(context, () -> 0);
//                        System.exit(exitCode);
//                    }
//
//                    commandProcessor.processCommand(input.trim());
//                }
//            }
//        };
//    }
}
