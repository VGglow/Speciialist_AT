import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("Тестовый набор \"CreditCalcTests\" - Проверка кредитного калькулятора")
public class CreditCalcTests {
    String[] browsers = {"chrome", "firefox", "edge"};

    @BeforeAll
    static void before_all(TestInfo test_info) {
        System.out.println("\n======== " + test_info.getDisplayName() + " - начали выполнение. ========\n");
//        Configuration.browser = "edge"; // "chrome", "firefox", "edge", "ie"
    }

    @AfterAll
    static void after_all(TestInfo test_info) {
        System.out.println("\n======== " + test_info.getDisplayName() + " - закончили выполнение. ========");
    }

    @BeforeEach
    void beforeEach(TestInfo test_info) {
        String browser = browsers[new Random().nextInt(3)];
//        String browser = "edge";
        Configuration.browser = browser;
        Configuration.browserSize = "1280x900";
        Configuration.browserPosition = "20x20";
//        Configuration.webdriverLogsEnabled = true;
        open("https://slqamsk.github.io/cases/loan-calc/v01");
        System.out.println("  === Начали тест " + test_info.getDisplayName() + " в браузере " + browser + ". ===");
    }

    @AfterEach
    void afterEach(TestInfo test_info) {
        closeWindow();
        System.out.println("\n  === Закончили тест " + test_info.getDisplayName() + ". ===\n");
    }

    @DisplayName("01. Простейший тест: Заполнить 3 поля и нажать кнопку \"Рассчитать\"")
    @Test
    @Disabled
    void simpleCalcTest() {
//        open("https://slqamsk.github.io/cases/loan-calc/v01");
        $(By.id("amount")).setValue("100000");
        $(By.id("term")).setValue("12");
        $(By.id("rate")).setValue("16.5");
        $(By.id("calculate-btn")).click();
        sleep(5_000);
    }

    @ParameterizedTest(name = "02. Дождаться вычисления результата и проверить значения.")
    @CsvSource({
            "edge, 1000, 3, 12.55",
            "chrome, 200000, 7, 15.99",
            "firefox, 3000000, 13, 19.723"
    })
    @Disabled
    void secondCalcTest(String browser, String amount, String term, String rate) {
//        Configuration.browser = browser;
//        open("https://slqamsk.github.io/cases/loan-calc/v01");
        $(By.id("amount")).sendKeys(amount);
        $(By.id("term")).sendKeys(term);
        $(By.id("rate")).sendKeys(rate);
//        $(By.name("paymentType")).selectRadio("diff");
        $(By.id("calculate-btn")).click();
        $(By.id("amount-hint")).shouldNotBe(cssClass("error"));
        $(By.id("term-hint")).shouldNotBe(cssClass("error"));
        $(By.id("rate-hint")).shouldNotBe(cssClass("error"));
        $(By.id("calculation-modal")).shouldBe(visible);
        $(By.id("calculation-modal")).shouldNotBe(visible, Duration.ofSeconds(11));
        $(By.id("results-container")).shouldBe(visible);
        double monthlyRate = Double.parseDouble(rate) / 12 / 100;
        double dblTerm = Double.parseDouble(term);
        double monthlyPayment = Double.parseDouble(amount) *
                (monthlyRate * Math.pow(1 + monthlyRate, dblTerm)) /
                (Math.pow(1 + monthlyRate, dblTerm) - 1);
        String strMonthlyPayment = String.valueOf(Math.round(monthlyPayment * 100) / 100);
        $(By.id("result-amount")).shouldHave(text(amount));
        $(By.id("result-term")).shouldHave(text(term));
        $(By.id("result-rate")).shouldHave(text(rate));
        $(By.id("result-payment-type")).shouldHave(text("Аннуитетный"));
        $(By.id("monthly-payment")).shouldHave(text(strMonthlyPayment));
//        sleep(5000);
    }

    @ParameterizedTest(name = "03/04. Открыть график платежей и убедиться, что туда корректно перенесены данные " +
            "со страницы результатов расчёта. Добавить 2-й параметризованный автотест")
    @CsvSource({
            "edge, 1000, 3, 12.55",
            "chrome, 200000, 7, 15.99",
            "firefox, 3330333, 9, 19.7123"
    })
    void thirdCalcTest(String browser, String amount, String term, String rate) {
//        Configuration.browser = browser;
//        open("https://slqamsk.github.io/cases/loan-calc/v01");
        System.out.println("Aгрументы:\n - " + amount + ".00 р.\n - " + term + " мес.\n - " + rate + "%");
        $(By.id("amount")).sendKeys(amount);
        $(By.id("term")).sendKeys(term);
        $(By.id("rate")).sendKeys(rate);
//        $(By.name("paymentType")).selectRadio("diff");
        $(By.id("calculate-btn")).click();
        $(By.id("amount-hint")).shouldNotBe(cssClass("error"));
        $(By.id("term-hint")).shouldNotBe(cssClass("error"));
        $(By.id("rate-hint")).shouldNotBe(cssClass("error"));
        $(By.id("calculation-modal")).shouldBe(visible);
        $(By.id("calculation-modal")).shouldNotBe(visible, Duration.ofSeconds(11));
        $(By.id("results-container")).shouldBe(visible);
        double monthlyRate = Double.parseDouble(rate) / 12 / 100;
        double dblTerm = Double.parseDouble(term);
        double monthlyPayment = Double.parseDouble(amount) *
                (monthlyRate * Math.pow(1 + monthlyRate, dblTerm)) /
                (Math.pow(1 + monthlyRate, dblTerm) - 1);
        String strMonthlyPayment = String.valueOf(Math.round(monthlyPayment * 100) / 100);
        $(By.id("result-amount")).shouldHave(text(amount));
        $(By.id("result-term")).shouldHave(text(term));
        $(By.id("result-rate")).shouldHave(text(rate));
        $(By.id("result-payment-type")).shouldHave(text("Аннуитетный"));
        $(By.id("monthly-payment")).shouldHave(text(strMonthlyPayment));
        $(By.id("show-schedule-btn")).click();
        switchTo().window(1);
        $(By.tagName("h2")).shouldHave(text("График платежей"));
        $x("//p[text()='Сумма кредита: ']/span").shouldHave(text(amount));
        $x("//p[text()='Срок кредита: ']/span").shouldHave(text(term));
        $x("//p[text()='Процентная ставка: ']/span").shouldHave(text(rate));
        $x("//p[text()='Тип платежа: ']/span").shouldHave(text("Аннуитетный"));
//        sleep(5000);
    }

    @ParameterizedTest(name = "5.1 Тест верификации вводимых данных")
    @CsvSource({
            "null, 3, 12.55, amount",
            "0, 3, 12.55, amount",
            "999, 3, 12.55, amount",
            "10000001, 3, 12.55, amount",
            "100.000-01, 3, 12.55, amount",
            "200000, 0, 15.99, term",
            "200000, 361, 15.99, term",
            "200000, 3 6 6, 15.99, term",
            "3330333, 9, 0.001, rate",
            "3330333, 9, 100.01, rate",
            "3330333, 9, -1, rate",
            "3330333, 9, %$^$^, rate",
    })
    void verifyCalcTest(String amount, String term, String rate, String field) {
        System.out.println("Aгрументы:\n - >" + amount + "<\n - >" + term + "<\n - >" + rate + "<");
        $(By.id("amount")).sendKeys(amount);
        $(By.id("term")).sendKeys(term);
        $(By.id("rate")).sendKeys(rate);
        $(By.id("calculate-btn")).click();
        $(By.id(field + "-hint")).shouldBe(cssClass("error"));
//        sleep(3000);
    }

    @ParameterizedTest(name = "5.2 Открыть график платежей и убедиться, что туда корректно перенесены данные " +
            "со страницы результатов расчёта. Дифференцированный платёж")
    @CsvSource({
            "1000, 3, 12.55",
            "200000, 7, 15.99",
            "3330333, 9, 19.7123",
    })
    void diffCalcTest(String amount, String term, String rate) {
        System.out.println("Aгрументы:\n - " + amount + ".00 р.\n - " + term + " мес.\n - " + rate + "%");
        $(By.id("amount")).sendKeys(amount);
        $(By.id("term")).sendKeys(term);
        $(By.id("rate")).sendKeys(rate);
        $(By.name("paymentType")).selectRadio("diff");
        $(By.id("calculate-btn")).click();
        $(By.id("amount-hint")).shouldNotBe(cssClass("error"));
        $(By.id("term-hint")).shouldNotBe(cssClass("error"));
        $(By.id("rate-hint")).shouldNotBe(cssClass("error"));
        $(By.id("calculation-modal")).shouldBe(visible);
        $(By.id("calculation-modal")).shouldNotBe(visible, Duration.ofSeconds(11));
        $(By.id("results-container")).shouldBe(visible);
        double dblAmount = Double.parseDouble(amount);
        double dblRate = Double.parseDouble(rate);
        int intTerm = Integer.parseInt(term);
        double monthlyRate = dblRate / 12 / 100;
        double principalPayment = dblAmount / intTerm;
        double firstPayment = 0.0;
        double lastPayment = 0.0;
        double totalPayment = 0.0;
        for (int month = 1; month <= intTerm; month++) {
            double interestPayment = (dblAmount - (principalPayment * (month - 1))) * monthlyRate;
            double monthlyPayment = principalPayment + interestPayment;
            if (month == 1) firstPayment = monthlyPayment;
            if (month == intTerm) lastPayment = monthlyPayment;
            totalPayment += monthlyPayment;
        }
        double overpayment = totalPayment - dblAmount;
        String strFirstPayment = String.valueOf(Math.round(firstPayment * 100) / 100);
        String strLastPayment = String.valueOf(Math.round(lastPayment * 100) / 100);
        String strTotalPayment = String.valueOf(Math.round(totalPayment * 100) / 100);
        String strOverpayment = String.valueOf(Math.round(overpayment * 100) / 100);
        $(By.id("result-amount")).shouldHave(text(amount));
        $(By.id("result-term")).shouldHave(text(term));
        $(By.id("result-rate")).shouldHave(text(rate));
        $(By.id("result-payment-type")).shouldHave(text("Дифференцированный"));
        $(By.id("first-payment")).shouldHave(text(strFirstPayment));
        $(By.id("last-payment")).shouldHave(text(strLastPayment));
        $(By.id("overpayment")).shouldHave(text(strOverpayment));
        $(By.id("total-payment")).shouldHave(text(strTotalPayment));
        $(By.id("show-schedule-btn")).click();
        switchTo().window(1);
        $(By.tagName("h2")).shouldHave(text("График платежей"));
        $x("//p[text()='Сумма кредита: ']/span").shouldHave(text(amount));
        $x("//p[text()='Срок кредита: ']/span").shouldHave(text(term));
        $x("//p[text()='Процентная ставка: ']/span").shouldHave(text(rate));
        $x("//p[text()='Тип платежа: ']/span").shouldHave(text("Дифференцированный"));
//        sleep(5000);
    }
}

