package com.epam.automation;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GitTest {

    public static final String ALERT_DEPARTURE_EMPTY = "Выберите место отправления из выпадающего списка";
    public static final String ALERT_ARRIVAL_EMPTY = "Выберите место прибытия из выпадающего списка";
    public static final String AUSTR = "австрия";
    public static final String MINSK = "минск";
    public static final String MINSK_PASS = "Минск-Пассажирский";
    public static final String KOBRIN = "кобрин";
    public static final String KOBRIN_FULL = "Кобрин";
    public static final String INSERT_DATA_PLS = "Пожалуйста, введите верный e-mail и телефон";
    public static final String MINSK_ENG = "minsk";
    public static final String TRAINS_NOT_FOUND = "Поездов не найдено";
    public static final String EMAIL = "kriket267@gmail.com";
    public static final String PASSWORD_WRONG = "qwerty";
    public static final String PASSWORD = "akftJv99";

    @Test
    public void emptyFieldsTest() {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();

        chrome.findElement(By.xpath("//input[@id='result-find']")).click();

        try {
            Alert alert = chrome.switchTo().alert();
            Assert.assertEquals(alert.getText(), ALERT_DEPARTURE_EMPTY);
            alert.accept();
            chrome.quit();
        } catch (NoAlertPresentException e) {
            chrome.quit();
            Assert.fail();
        }

    }

    @Test
    public void nonExistentRouteTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();


        chrome.findElement(By.xpath("//input[@id='input-from']")).sendKeys(MINSK);
        Thread.sleep(500);
        chrome.findElement(By.xpath("//div[@class='station'][@data-text='" + MINSK_PASS + "']")).click();
        chrome.findElement(By.xpath("//input[@id='input-from']")).sendKeys(AUSTR);
        chrome.findElement(By.xpath("//input[@id='result-find']")).click();

        try {
            Alert alert = chrome.switchTo().alert();
            Assert.assertEquals(alert.getText(), ALERT_ARRIVAL_EMPTY);
            alert.accept();
            chrome.quit();
        } catch (NoAlertPresentException e) {
            chrome.quit();
            Assert.fail();
        }

    }

    @Test
    public void validDateTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();


        chrome.findElement(By.xpath("//input[@id='input-from']")).sendKeys(MINSK);
        Thread.sleep(500);
        chrome.findElement(By.xpath("//div[@class='station'][@data-text='" + MINSK_PASS + "']")).click();
        chrome.findElement(By.xpath("//input[@id='input-to']")).sendKeys(KOBRIN);
        Thread.sleep(500);
        chrome.findElement(By.xpath("//div[@class='station'][@data-text='" + KOBRIN_FULL + "']")).click();

        String before = chrome.findElement(By.xpath("//input[@id='zhd-date']")).getText();
        Thread.sleep(500);
        chrome.findElement(By.xpath("//input[@id='zhd-date']")).click();
        Calendar c = new GregorianCalendar();
        Integer day = c.get(Calendar.DAY_OF_MONTH);

        chrome.findElements(By.xpath("//*[@class='ui-state-default'][text()='" + day + "']")).get(0).click();
        Thread.sleep(1000);
        String after = chrome.findElement(By.xpath("//input[@id='zhd-date']")).getText();

        Assert.assertEquals(after, before);

        chrome.quit();
    }

    @Test
    public void emptyFieldsAuthTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();

        chrome.findElement(By.xpath("//img[@class='li_img'][@alt='Личный кабинет']")).click();
        chrome.findElement(By.xpath("//input[@class='input-form-log-in']")).click();

        Thread.sleep(500);

        String text = chrome.findElement(By.xpath("//span[. = 'Пожалуйста, введите верный e-mail и телефон']")).getText();

        Assert.assertEquals(text, INSERT_DATA_PLS);
        chrome.quit();

    }

    @Test
    public void wrongPassAuthTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();

        chrome.findElement(By.xpath("//img[@class='li_img'][@alt='Личный кабинет']")).click();


        chrome.findElement(By.xpath("//input[@id='input-email']")).sendKeys(EMAIL);
        chrome.findElement(By.xpath("//input[@id='input-pass']")).sendKeys(PASSWORD_WRONG);
        chrome.findElement(By.xpath("//input[@class='input-form-log-in']")).click();

        Thread.sleep(500);

        String emailField = chrome.findElement(By.xpath("//input[@id='input-email']")).getText();
        String passField = chrome.findElement(By.xpath("//input[@id='input-pass']")).getText();


        Assert.assertEquals(emailField, "");
        Assert.assertEquals(passField, "");
        chrome.quit();

    }

    @Test
    public void correctPassAuthTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();

        chrome.findElement(By.xpath("//img[@class='li_img'][@alt='Личный кабинет']")).click();


        chrome.findElement(By.xpath("//input[@id='input-email']")).sendKeys(EMAIL);
        chrome.findElement(By.xpath("//input[@id='input-pass']")).sendKeys(PASSWORD);
        chrome.findElement(By.xpath("//input[@class='input-form-log-in']")).click();

        Thread.sleep(500);

        String yourEmail = chrome.findElement(By.xpath("//div[@class='zhd-w-caption']")).getText();

        Assert.assertEquals(yourEmail, EMAIL);
        chrome.quit();

    }

    @Test
    public void exitAuthTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();

        chrome.findElement(By.xpath("//img[@class='li_img'][@alt='Личный кабинет']")).click();


        chrome.findElement(By.xpath("//input[@id='input-email']")).sendKeys(EMAIL);
        chrome.findElement(By.xpath("//input[@id='input-pass']")).sendKeys(PASSWORD);
        chrome.findElement(By.xpath("//input[@class='input-form-log-in']")).click();

        Thread.sleep(500);

        String yourEmail = chrome.findElement(By.xpath("//div[@class='zhd-w-caption']")).getText();

        chrome.findElement(By.xpath("//a[@class='acc-exit question']")).click();

        String emailField = chrome.findElement(By.xpath("//input[@id='input-email']")).getText();
        String passField = chrome.findElement(By.xpath("//input[@id='input-pass']")).getText();

        Assert.assertEquals(yourEmail, EMAIL);
        Assert.assertEquals(emailField, "");
        Assert.assertEquals(passField, "");
        chrome.quit();

    }

    @Test
    public void coincidingCitiesTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();


        chrome.findElement(By.xpath("//input[@id='input-from']")).sendKeys(MINSK);
        Thread.sleep(500);
        chrome.findElement(By.xpath("//div[@class='station'][@data-text='" + MINSK_PASS + "']")).click();
        chrome.findElement(By.xpath("//input[@id='input-to']")).sendKeys(MINSK);
        Thread.sleep(500);
        (chrome.findElements(By.xpath("//div[@class='station'][@data-text='" + MINSK_PASS + "']"))).get(1).click();
        Thread.sleep(500);

        chrome.findElement(By.xpath("//input[@id='result-find']")).click();
        Thread.sleep(500);
        String resultText = chrome.findElement(By.xpath("//span[@class='zhd-a-header-text']")).getText();

        Assert.assertNotEquals(resultText, TRAINS_NOT_FOUND);

        chrome.quit();
    }

    @Test
    public void findTicketsTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();


        chrome.findElement(By.xpath("//input[@id='input-from']")).sendKeys(MINSK);
        Thread.sleep(500);
        chrome.findElement(By.xpath("//div[@class='station'][@data-text='" + MINSK_PASS + "']")).click();
        chrome.findElement(By.xpath("//input[@id='input-to']")).sendKeys(KOBRIN);
        Thread.sleep(500);
        chrome.findElement(By.xpath("//div[@class='station'][@data-text='" + KOBRIN_FULL + "']")).click();
        Thread.sleep(500);

        chrome.findElement(By.xpath("//input[@id='result-find']")).click();
        Thread.sleep(500);
        String resultText = chrome.findElement(By.xpath("//span[@class='zhd-a-header-text']")).getText();

        Assert.assertNotEquals(resultText, TRAINS_NOT_FOUND);

        chrome.quit();
    }

    @Test
    public void changeDirectionTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();


        chrome.findElement(By.xpath("//input[@id='input-from']")).sendKeys(MINSK);
        Thread.sleep(500);
        chrome.findElement(By.xpath("//div[@class='station'][@data-text='" + MINSK_PASS + "']")).click();
        chrome.findElement(By.xpath("//input[@id='input-to']")).sendKeys(KOBRIN);
        Thread.sleep(500);
        chrome.findElement(By.xpath("//div[@class='station'][@data-text='" + KOBRIN_FULL + "']")).click();
        Thread.sleep(500);

        String derpature = chrome.findElement(By.xpath("//input[@id='input-from']")).getText();
        String arrival = chrome.findElement(By.xpath("//input[@id='input-to']")).getText();

        chrome.findElement(By.xpath("//div[@class='zhd-swap']")).click();
        Thread.sleep(500);

        String derpatureNew = chrome.findElement(By.xpath("//input[@id='input-from']")).getText();
        String arrivalNew = chrome.findElement(By.xpath("//input[@id='input-to']")).getText();

        Assert.assertEquals(derpatureNew, arrival);
        Assert.assertEquals(arrivalNew, derpature);

        chrome.quit();
    }

    @Test
    public void insertEngTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();


        chrome.findElement(By.xpath("//input[@id='input-from']")).sendKeys(MINSK_ENG);
        Thread.sleep(500);

        String derpature = chrome.findElement(By.xpath("//input[@id='input-from']")).getText();

        Assert.assertNotEquals(derpature, MINSK_ENG);

        chrome.quit();
    }
/*
    @Test
    public void GitLoginTest() {
        System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");

        WebDriver chrome = new ChromeDriver();
        chrome.get("http://miykvytok.com/train");
        chrome.manage().window().maximize();

        chrome.findElement(By.xpath("//a[text()=\"Sign in\"]")).click();
        chrome.findElement(By.xpath("//input[@name='login']")).sendKeys(LOGIN);
        chrome.findElement(By.xpath("//input[@name='password']")).sendKeys(PASSWORD);
        chrome.findElement(By.xpath("//input[@name='commit']")).click();

        String username = chrome.findElement(By.xpath("//meta[@name='user-login']")).getAttribute("content");
        Assert.assertEquals(username, LOGIN);

        chrome.quit();
    }*/
}
