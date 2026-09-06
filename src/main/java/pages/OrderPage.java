package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object формы заказа самоката (шаги «Для кого самокат» и «Про аренду»)
 * и модальных окон подтверждения / успешного оформления заказа.
 */
public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // --- Шаг 1. «Для кого самокат» ---

    private static final By NAME_INPUT = By.xpath("//input[@placeholder='* Имя']");
    private static final By SURNAME_INPUT = By.xpath("//input[@placeholder='* Фамилия']");
    private static final By ADDRESS_INPUT = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private static final By METRO_INPUT = By.xpath("//input[@placeholder='* Станция метро']");
    private static final By PHONE_INPUT = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private static final By NEXT_BUTTON = By.xpath("//button[text()='Далее']");
    private static final By STEP1_ERRORS = By.xpath("//div[contains(@class,'Order_Form')]//div[contains(text(),'Введите')]");

    public void fillName(String name) {
        driver.findElement(NAME_INPUT).sendKeys(name);
    }

    public void fillSurname(String surname) {
        driver.findElement(SURNAME_INPUT).sendKeys(surname);
    }

    public void fillAddress(String address) {
        driver.findElement(ADDRESS_INPUT).sendKeys(address);
    }

    public void fillPhone(String phone) {
        driver.findElement(PHONE_INPUT).sendKeys(phone);
    }

    /**
     * Вводит название станции метро и выбирает подходящий пункт выпадающего списка.
     */
    public void selectMetroStation(String stationName) {
        WebElement metroInput = driver.findElement(METRO_INPUT);
        metroInput.click();
        metroInput.sendKeys(stationName);

        By option = By.xpath("//div[@class='select-search__select']//button[text()='" + stationName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON)).click();
    }

    /**
     * Заполняет первый шаг формы заказа и переходит ко второму шагу.
     */
    public void fillStepOne(String name, String surname, String address, String metroStation, String phone) {
        fillName(name);
        fillSurname(surname);
        fillAddress(address);
        selectMetroStation(metroStation);
        fillPhone(phone);
        clickNextButton();
    }

    public List<WebElement> getStepOneErrors() {
        return driver.findElements(STEP1_ERRORS);
    }

    private static final By NAME_ERROR = By.xpath("//div[text()='Введите корректное имя']");
    private static final By SURNAME_ERROR = By.xpath("//div[text()='Введите корректную фамилию']");
    private static final By ADDRESS_ERROR = By.xpath("//div[text()='Введите корректный адрес']");
    private static final By PHONE_ERROR = By.xpath("//div[text()='Введите корректный номер']");

    public boolean isNameErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(NAME_ERROR)).isDisplayed();
    }

    public boolean isSurnameErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SURNAME_ERROR)).isDisplayed();
    }

    public boolean isAddressErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ADDRESS_ERROR)).isDisplayed();
    }

    public boolean isPhoneErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PHONE_ERROR)).isDisplayed();
    }

    // --- Шаг 2. «Про аренду» ---

    private static final By DATE_INPUT = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private static final By AVAILABLE_DAY = By.cssSelector(
            ".react-datepicker__day:not(.react-datepicker__day--disabled):not(.react-datepicker__day--outside-month)");
    private static final By RENT_PERIOD_DROPDOWN = By.cssSelector(".Dropdown-control");
    private static final By COMMENT_INPUT = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private static final By BACK_BUTTON = By.xpath("//button[text()='Назад']");
    private static final By ORDER_SUBMIT_BUTTON =
            By.xpath("//div[contains(@class,'Order_Buttons')]/button[text()='Заказать']");

    public void selectNearestAvailableDate() {
        driver.findElement(DATE_INPUT).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AVAILABLE_DAY));
        List<WebElement> days = driver.findElements(AVAILABLE_DAY);
        days.get(0).click();
    }

    public void selectRentPeriod(String period) {
        driver.findElement(RENT_PERIOD_DROPDOWN).click();
        By option = By.xpath("//div[@class='Dropdown-option' and text()='" + period + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(option)).click();
    }

    public void selectColor(String colorId) {
        WebElement checkbox = driver.findElement(By.id(colorId));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
        js.executeScript("arguments[0].click();", checkbox);
    }

    public void fillComment(String comment) {
        driver.findElement(COMMENT_INPUT).sendKeys(comment);
    }

    public void clickBackButton() {
        wait.until(ExpectedConditions.elementToBeClickable(BACK_BUTTON)).click();
    }

    public void clickOrderSubmitButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(ORDER_SUBMIT_BUTTON));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        button.click();
    }

    /**
     * Заполняет второй шаг формы заказа и нажимает «Заказать».
     */
    public void fillStepTwo(String rentPeriod, String colorId, String comment) {
        selectNearestAvailableDate();
        selectRentPeriod(rentPeriod);
        selectColor(colorId);
        fillComment(comment);
        clickOrderSubmitButton();
    }

    // --- Модальные окна ---

    private static final By CONFIRM_MODAL_TITLE = By.xpath("//div[text()='Хотите оформить заказ?']");
    private static final By CONFIRM_YES_BUTTON =
            By.xpath("//div[text()='Хотите оформить заказ?']/ancestor::div[contains(@class,'Order_Modal')]//button[text()='Да']");
    private static final By CONFIRM_NO_BUTTON =
            By.xpath("//div[text()='Хотите оформить заказ?']/ancestor::div[contains(@class,'Order_Modal')]//button[text()='Нет']");
    private static final By SUCCESS_MODAL_TITLE = By.xpath("//div[text()='Заказ оформлен']");

    public boolean isConfirmModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CONFIRM_MODAL_TITLE)).isDisplayed();
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(CONFIRM_YES_BUTTON)).click();
    }

    public void declineOrderConfirmation() {
        wait.until(ExpectedConditions.elementToBeClickable(CONFIRM_NO_BUTTON)).click();
    }

    public boolean isSuccessModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MODAL_TITLE)).isDisplayed();
    }
}
