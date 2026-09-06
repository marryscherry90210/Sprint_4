package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object страницы статуса заказа (открывается после ввода номера заказа
 * и клика по кнопке «Go!» на главной странице, адрес вида /track?t=НОМЕР).
 */
public class OrderStatusPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By LOOK_BUTTON = By.xpath("//button[text()='Посмотреть']");
    private static final By NO_SUCH_ORDER_MESSAGE = By.xpath("//*[text()='Такого заказа нет']");

    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickLookButton() {
        wait.until(ExpectedConditions.elementToBeClickable(LOOK_BUTTON)).click();
    }

    public boolean isNoSuchOrderMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(NO_SUCH_ORDER_MESSAGE)).isDisplayed();
    }
}
