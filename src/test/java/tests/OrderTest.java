package tests;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pages.MainPage;
import pages.OrderPage;

import static org.junit.Assert.assertTrue;

/**
 * Позитивный сценарий заказа самоката: заполнение формы (два шага),
 * подтверждение заказа и проверка всплывающего окна об успешном оформлении.
 * <p>
 * Сценарий проверяется с двух точек входа (кнопка «Заказать» вверху и внизу страницы)
 * и минимум с двумя наборами тестовых данных — итого 4 параметризованных прогона.
 */
@RunWith(JUnitParamsRunner.class)
public class OrderTest extends BaseTest {

    // Наборы тестовых данных: имя, фамилия, адрес, станция метро, телефон, срок аренды, id цвета, комментарий
    private Object[] orderData() {
        return new Object[]{
                new Object[]{
                        "Марина", "Сатина", "Москва, ул. Пушкина, д. 1",
                        "Кутузовская", "+79001234567", "трое суток", "black", "Позвоните за час"
                },
                new Object[]{
                        "Ирина", "Иванова", "Москва, Ленинский проспект, д. 10",
                        "Академическая", "+79107654321", "сутки", "grey", "Домофон не работает"
                },
        };
    }

    @Test
    @Parameters(method = "orderData")
    public void canOrderScooterUsingTopButton(String name, String surname, String address, String metro,
                                               String phone, String rentPeriod, String colorId, String comment) {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();
        mainPage.clickOrderButtonTop();

        orderScooterAndCheckSuccess(name, surname, address, metro, phone, rentPeriod, colorId, comment);
    }

    @Test
    @Parameters(method = "orderData")
    public void canOrderScooterUsingBottomButton(String name, String surname, String address, String metro,
                                                  String phone, String rentPeriod, String colorId, String comment) {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();
        mainPage.clickOrderButtonBottom();

        orderScooterAndCheckSuccess(name, surname, address, metro, phone, rentPeriod, colorId, comment);
    }

    private void orderScooterAndCheckSuccess(String name, String surname, String address, String metro,
                                              String phone, String rentPeriod, String colorId, String comment) {
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillStepOne(name, surname, address, metro, phone);
        orderPage.fillStepTwo(rentPeriod, colorId, comment);

        assertTrue("Не появилась модалка подтверждения заказа", orderPage.isConfirmModalDisplayed());
        orderPage.confirmOrder();

        assertTrue("Не появилось окно об успешном оформлении заказа", orderPage.isSuccessModalDisplayed());
    }
}
