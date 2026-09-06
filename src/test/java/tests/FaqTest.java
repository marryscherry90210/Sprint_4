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
                new Object[]{0, "Сутки — 400 рублей"},
                new Object[]{1, "один заказ — один самокат"},
                new Object[]{2, "вы оформляете заказ на 8 мая"},
                new Object[]{3, "Только начиная с завтрашнего дня"},
                new Object[]{4, "Пока что нет"},
                new Object[]{5, "Самокат приезжает к вам с полной зарядкой"},
                new Object[]{6, "Да, пока самокат не привезли"},
                new Object[]{7, "Да, обязательно"},
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
