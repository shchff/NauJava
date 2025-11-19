package ru.grigorii.NauJava.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Login and Logout test")
public class LoginLogoutTest
{
    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    static void setUpClass()
    {
        WebDriverManager.chromiumdriver().setup();
    }

    @BeforeEach
    void setUpTest()
    {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }

    private void login()
    {
        driver.get("http://localhost:8080/login");

        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("email"))
        );
        emailField.sendKeys("grisaserbakov0@gmail.com");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("12345");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
    }

    private void logout()
    {
        WebElement logoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("logout-button"))
        );
        logoutButton.click();
    }

    @Test
    @DisplayName("Login")
    void shouldSuccessfullyLogin()
    {
        login();

        WebElement mainTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("title"))
        );

        assertEquals("Главная", mainTitle.getText());
    }

    @Test
    @DisplayName("Logout")
    void shouldLogoutSuccessfully()
    {
        login();
        logout();

        WebElement loginTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("title"))
        );

        assertEquals("Вход", loginTitle.getText());
    }
}
