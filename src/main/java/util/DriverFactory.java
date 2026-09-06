package util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Создаёт экземпляр WebDriver для нужного браузера.
 * Браузер задаётся системным свойством browser (chrome | firefox), по умолчанию — chrome.
 * Пример запуска в Firefox: mvn test -Dbrowser=firefox
 */
public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver(String browser) {
        String browserName = browser == null ? "chrome" : browser.toLowerCase();

        switch (browserName) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new FirefoxDriver(firefoxOptions);
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(chromeOptions);
        }
    }

    public static WebDriver createDriver() {
        return createDriver(System.getProperty("browser", "chrome"));
    }
}
