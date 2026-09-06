package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Page Object главной страницы сервиса «Яндекс Самокат».
 */
public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public static final String URL = "https://qa-scooter.praktikum-services.ru/";

    // Логотипы
    private static final By YANDEX_LOGO = By.cssSelector(".Header_LogoYandex__3TSOI");
    private static final By SCOOTER_LOGO = By.cssSelector(".Header_LogoScooter__3lsAR");

    // Точки входа в заказ
    private static final By ORDER_BUTTON_TOP = By.xpath("(//button[text()='Заказать'])[1]");
    private static final By ORDER_BUTTON_BOTTOM = By.xpath("(//button[text()='Заказать'])[2]");

    // Статус заказа (форма на главной странице)
    private static final By ORDER_STATUS_BUTTON = By.xpath("//button[text()='Статус заказа']");
    private static final By ORDER_NUMBER_INPUT = By.cssSelector("input[placeholder='Введите номер заказа']");
    private static final By GO_BUTTON = By.xpath("//button[text()='Go!']");

    // Cookie-баннер
    private static final By COOKIE_ACCEPT_BUTTON = By.xpath("//button[text()='да все привыкли']");

    // FAQ
    private static final By ACCORDION_HEADINGS = By.cssSelector(".accordion__heading");
    private static final By ACCORDION_BUTTONS = By.cssSelector(".accordion__button");
    private static final By ACCORDION_PANELS = By.cssSelector(".accordion__panel");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public MainPage open() {
        driver.get(URL);
        return this;
    }

    /**
     * Закрывает баннер с cookie, если он отображается на странице.
     */
    public MainPage acceptCookies() {
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(COOKIE_ACCEPT_BUTTON));
            cookieButton.click();
        } catch (Exception ignored) {
            // Баннер уже закрыт или не появился — ничего страшного
        }
        return this;
    }

    public void clickOrderButtonTop() {
        wait.until(ExpectedConditions.elementToBeClickable(ORDER_BUTTON_TOP)).click();
    }

    public void clickOrderButtonBottom() {
        WebElement button = driver.findElement(ORDER_BUTTON_BOTTOM);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        wait.until(ExpectedConditions.elementToBeClickable(ORDER_BUTTON_BOTTOM)).click();
    }

    public WebElement getYandexLogo() {
        return driver.findElement(YANDEX_LOGO);
    }

    public WebElement getScooterLogo() {
        return driver.findElement(SCOOTER_LOGO);
    }

    public void clickYandexLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(YANDEX_LOGO)).click();
    }

    public void clickScooterLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(SCOOTER_LOGO)).click();
    }

    /**
     * Переключается на последнюю открытую вкладку браузера.
     */
    public void switchToNewTab() {
        Set<String> tabs = driver.getWindowHandles();
        String lastTab = tabs.toArray(new String[0])[tabs.size() - 1];
        driver.switchTo().window(lastTab);
    }

    /**
     * Ожидает, пока откроется новая вкладка браузера (например, после клика по внешней ссылке).
     */
    public void waitForNewTabToOpen() {
        wait.until(d -> d.getWindowHandles().size() > 1);
    }

    /**
     * Ожидает, пока текущий URL не станет равен ожидаемому.
     */
    public void waitForUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Ожидает, пока текущий URL не станет содержать указанную подстроку.
     */
    public void waitForUrlToContain(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    /**
     * Возвращает адрес страницы, открытой в текущей активной вкладке.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    // ---Статус заказа ---

    public void openOrderStatusForm() {
        wait.until(ExpectedConditions.elementToBeClickable(ORDER_STATUS_BUTTON)).click();
    }

    public void enterOrderNumber(String orderNumber) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_NUMBER_INPUT));
        input.click();
        input.sendKeys(orderNumber);
    }

    public void clickGoButton() {
        wait.until(ExpectedConditions.elementToBeClickable(GO_BUTTON)).click();
    }

    // ---FAQ ---

    public List<WebElement> getAccordionButtons() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ACCORDION_BUTTONS));
        return driver.findElements(ACCORDION_BUTTONS);
    }

    public List<WebElement> getAccordionHeadings() {
        return driver.findElements(ACCORDION_HEADINGS);
    }

    /**
     * Кликает по вопросу аккордеона с указанным индексом (начиная с 0)
     * и возвращает текст открывшегося ответа.
     */
    public String openFaqAnswer(int index) {
        List<WebElement> buttons = getAccordionButtons();
        WebElement button = buttons.get(index);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        button.click();

        List<WebElement> panels = driver.findElements(ACCORDION_PANELS);
        WebElement panel = panels.get(index);
        wait.until(ExpectedConditions.visibilityOf(panel));
        return panel.getText();
    }

    public String getAccordionQuestionText(int index) {
        return getAccordionButtons().get(index).getText();
    }

    public int getAccordionSize() {
        return getAccordionButtons().size();
    }
}
