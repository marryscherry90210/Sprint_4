package tests;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pages.MainPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Тесты раздела «Вопросы о важном» на главной странице.
 * Проверяем, что клик по стрелочке вопроса открывает соответствующий текст ответа.
 */
@RunWith(JUnitParamsRunner.class)
public class FaqTest extends BaseTest {

    private MainPage mainPage;

    // Параметризация: индекс вопроса в аккордеоне + ожидаемый (частично) текст ответа.
    private Object[] faqData() {
        return new Object[]{
                new Object[]{0,
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                new Object[]{1,
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, "
                                + "можете просто сделать несколько заказов — один за другим."},
                new Object[]{2,
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. "
                                + "Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. "
                                + "Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                new Object[]{3,
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                new Object[]{4,
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку "
                                + "по красивому номеру 1010."},
                new Object[]{5,
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — "
                                + "даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                new Object[]{6,
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже "
                                + "не попросим. Все же свои."},
                new Object[]{7,
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    @Test
    @Parameters(method = "faqData")
    public void clickingOnQuestionShowsCorrectAnswer(int questionIndex, String expectedAnswerPart) {
        mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();

        String actualAnswer = mainPage.openFaqAnswer(questionIndex);

        assertTrue(
                "Ответ на вопрос №" + questionIndex + " не содержит ожидаемый текст. Получено: " + actualAnswer,
                actualAnswer.contains(expectedAnswerPart)
        );
    }

    @Test
    public void accordionHasEightQuestions() {
        mainPage = new MainPage(driver).open();
        mainPage.acceptCookies();

        assertEquals(8, mainPage.getAccordionSize());
    }
}
