package tests;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pages.MainPage;
import pages.OrderStatusPage;

import static org.junit.Assert.assertTrue;

/**
 * Дополнительные тесты (факультативное задание, пункт 4):
 * если ввести неправильный номер заказа, должна открыться страница статуса заказа
 * с сообщением о том, что такого заказа нет.
 */
@RunWith(JUnitParamsRunner.class)
public class OrderStatusTest extends BaseTest {

    private Object[] invalidOrderNumbers() {
        return new Object[]{"999999999", "0", "abc123"};
    }

    @Test
    @Parameters(method = "invalidOrderNumbers")
    public void nonExistingOrderNumberShowsNoOrderMessage(String orderNumber) {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();

        mainPage.openOrderStatusForm();
        mainPage.enterOrderNumber(orderNumber);
        mainPage.clickGoButton();

        OrderStatusPage orderStatusPage = new OrderStatusPage(driver);
        orderStatusPage.clickLookButton();

        assertTrue("Ожидалось сообщение «Такого заказа нет»", orderStatusPage.isNoSuchOrderMessageDisplayed());
    }
}
