package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import util.DriverFactory;

/**
 * Базовый класс для всех тестов: поднимает и закрывает браузер.
 * Браузер выбирается системным свойством -Dbrowser=chrome|firefox (по умолчанию chrome).
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
