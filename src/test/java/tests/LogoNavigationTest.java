package tests;

import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

/**
 * Дополнительные тесты (факультативное задание, пункт 1-2):
 * переходы по логотипам в шапке сайта.
 */
public class LogoNavigationTest extends BaseTest {

    @Test
    public void clickingScooterLogoOpensMainPage() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();

        mainPage.clickOrderButtonTop(); // уходим со стартового состояния формы заказа
        mainPage.clickScooterLogo();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(MainPage.URL));

        assertTrue("После клика по логотипу Самоката должна открыться главная страница",
                driver.getCurrentUrl().equals(MainPage.URL));
    }

    @Test
    public void clickingYandexLogoOpensYandexInNewTab() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();

        String mainWindow = driver.getWindowHandle();
        mainPage.clickYandexLogo();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > 1);
        mainPage.switchToNewTab();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("yandex.ru"));

        assertTrue("В новой вкладке должен открыться сайт Яндекса",
                driver.getCurrentUrl().contains("yandex.ru"));

        driver.close();
        driver.switchTo().window(mainWindow);
    }
}
