package tests;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pages.MainPage;
import pages.OrderPage;

import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

/**
 * Дополнительные тесты (факультативное задание, пункт 3):
 * проверка ошибок валидации для полей формы заказа (шаг «Для кого самокат»).
 * Каждый прогон заполняет форму без одного обязательного поля и нажимает «Далее».
 */
@RunWith(JUnitParamsRunner.class)
public class OrderFormValidationTest extends BaseTest {

    private OrderPage orderPage;

    private Object[] missingFieldData() {
        return new Object[]{
                // пропущенное поле, имя, фамилия, адрес, телефон, проверка ошибки
                new Object[]{"имя", "", "Сатина", "Москва, ул. Пушкина, д. 1", "+79001234567",
                        (Supplier<Boolean>) () -> orderPage.isNameErrorDisplayed()},
                new Object[]{"фамилия", "Марина", "", "Москва, ул. Пушкина, д. 1", "+79001234567",
                        (Supplier<Boolean>) () -> orderPage.isSurnameErrorDisplayed()},
                new Object[]{"адрес", "Марина", "Сатина", "", "+79001234567",
                        (Supplier<Boolean>) () -> orderPage.isAddressErrorDisplayed()},
                new Object[]{"телефон", "Марина", "Сатина", "Москва, ул. Пушкина, д. 1", "",
                        (Supplier<Boolean>) () -> orderPage.isPhoneErrorDisplayed()},
        };
    }

    @Test
    @Parameters(method = "missingFieldData")
    public void emptyRequiredFieldShowsValidationError(String missingFieldName, String name, String surname,
                                                         String address, String phone,
                                                         Supplier<Boolean> errorCheck) {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();
        mainPage.clickOrderButtonTop();

        orderPage = new OrderPage(driver);
        if (!name.isEmpty()) {
            orderPage.fillName(name);
        }
        if (!surname.isEmpty()) {
            orderPage.fillSurname(surname);
        }
        if (!address.isEmpty()) {
            orderPage.fillAddress(address);
        }
        if (!phone.isEmpty()) {
            orderPage.fillPhone(phone);
        }
        orderPage.clickNextButton();

        assertTrue("Не появилось сообщение об ошибке для поля «" + missingFieldName + "»", errorCheck.get());
    }
}
