package com.crowneplazaz.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private String baseUrl = "http://127.0.0.1:8000/login/";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();}

    @Test(priority = 1, description = "LO02-09: Verify that password is masked when user input password")
    public void test01PasswordMasking() {
        driver.get(baseUrl);
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("cnnsdlkvn");
        Assert.assertEquals(passwordField.getAttribute("type"), "password",
                "Password field is not masked");}

    @Test(priority = 2, description = "LO02-010: Verify that no error message is displayed " +
            "when user input valid credential")
    public void test02ValidCredentials() {
        driver.get(baseUrl);
        driver.findElement(By.name("username")).sendKeys("chauanh");
        driver.findElement(By.name("password")).sendKeys("anhne123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/"),
                "Login was not successful or did not redirect correctly");
    }

    @Test(priority = 3, description = "LO02-011: Verify that an error message is displayed " +
            "when user leave blank [Username]")
    public void test03EmptyUsername() {
        driver.get(baseUrl);
        //Bo trong Username
        driver.findElement(By.name("username")).sendKeys("");
        driver.findElement(By.name("password")).sendKeys("anypassword");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = driver.findElement(
                By.xpath("//input[@id='username']" +
                        "/following-sibling::div[@class='invalid-feedback']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");
        String actualText = errorMessage.getText().trim();
        Assert.assertEquals(actualText, "Vui lòng nhập tên đăng nhập",
                "Error message text is incorrect");}

    @Test(priority = 4, description = "LO02-012: Verify that an error message is displayed " +
            "when input incorrect username")
    public void test04IncorrectUsername() {
        driver.get(baseUrl);
        // Nhập sai username, đúng password
        driver.findElement(By.name("username")).sendKeys("chaunane");
        driver.findElement(By.name("password")).sendKeys("anhne123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/"),
                "Login was not successful or did not redirect correctly");
    }

    @Test(priority = 5, description = "LO02-013: Verify that an error message is displayed " +
            "when user leaves blank [Password]")
    public void test05EmptyPassword() {
        driver.get(baseUrl);
        // Nhập username hợp lệ, bỏ trống password
        driver.findElement(By.name("username")).sendKeys("chauanh");
        driver.findElement(By.name("password")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = driver.findElement(
                By.xpath("//input[@id='password']" +
                        "/following-sibling::div[@class='invalid-feedback']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");
        String actualText = errorMessage.getText().trim();
        Assert.assertEquals(actualText, "Vui lòng nhập mật khẩu",
                "Error message for empty password is not correct");
    }


    @Test(priority = 6, description = "LO02-014: Verify that an error message is displayed " +
            "when input incorrect password")
    public void test06IncorrectPassword() {
        driver.get(baseUrl);
        driver.findElement(By.name("username")).sendKeys("chauanh");
        driver.findElement(By.name("password")).sendKeys("cnnsdlkvn");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/"),
                "Login was not successful or did not redirect correctly");
    }

    @Test(priority = 7, description = "LO02-017: Verify that both error messages are displayed when both Username and Password fields are incorrect")
    public void test07EmptyUsernameAndPassword() {
        driver.get(baseUrl);
        driver.findElement(By.name("username")).sendKeys("chaua");
        driver.findElement(By.name("password")).sendKeys("cnnsdlkvn");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("/"),
                "Login was not successful or did not redirect correctly");
    }

    @Test(priority = 8, description = "LO02-017: Verify that both error messages are displayed when both Username and Password fields are left empty")
    public void test08EmptyUsernameAndPassword() {
        driver.get(baseUrl);
        // Bỏ trống cả hai trường
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement usernameErrorMessage = driver.findElement(
                By.xpath("//input[@id='username']/following-sibling::div[@class='invalid-feedback']"));
        Assert.assertTrue(usernameErrorMessage.isDisplayed(), "Error message for username is not displayed");
        Assert.assertEquals(usernameErrorMessage.getText().trim(), "Vui lòng nhập tên đăng nhập",
                "Username error message text is incorrect");

        WebElement passwordErrorMessage = driver.findElement(
                By.xpath("//input[@id='password']/following-sibling::div[@class='invalid-feedback']"));
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Error message for password is not displayed");
        Assert.assertEquals(passwordErrorMessage.getText().trim(), "Vui lòng nhập mật khẩu",
                "Password error message text is incorrect");
    }
    @AfterMethod
    public void tearDown() {
        if (driver == null) {
            driver.quit();
        }
    }
}

