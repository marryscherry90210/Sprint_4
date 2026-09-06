package tests;

import org.junit.Test;
import pages.MainPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Дополнительные тесты (факультативное задание, пункт 1-2):
 * переходы по логотипам в шапке сайта.
 * Всё ожидание и работа с вкладками браузера вынесены в методы Page Object
 * ({@link MainPage}) — тест обращается только к готовым методам.
 */

public class LogoNavigationTest extends BaseTest {

    @Test
    public void clickingScooterLogoOpensMainPage() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();

        mainPage.clickOrderButtonTop(); // уходим со стартового состояния формы заказа
        mainPage.clickScooterLogo();
        mainPage.waitForUrlToBe(MainPage.URL);
        
        assertTrue("После клика по логотипу Самоката должна открыться главная страница",
                MainPage.URL, mainPage.getCurrentUrl());
    }

    @Test
    public void clickingYandexLogoOpensYandexInNewTab() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();

        mainPage.clickYandexLogo();
        mainPage.waitForNewTabToOpen();
        mainPage.switchToNewTab();
        mainPage.waitForUrlToContain("yandex.ru");
       
        assertEquals("В новой вкладке должен открыться сайт Яндекса",
                MainPage.URL, mainPage.getCurrentUrl());

        // Закрывать вкладку/браузер здесь не нужно — за это отвечает
        // tearDown() в BaseTest (аннотация @After), который вызывает driver.quit()
        // и закрывает вообще все открытые окна и вкладки.
    }
}
