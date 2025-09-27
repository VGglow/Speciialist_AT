import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SimpleAutoTest {
    @Test
    void test01() {
        open("http://92.51.36.108:7777/sl.qa");
        $("body").shouldHave(text("Учебные приложения"));
        sleep(1000);
    }

    @Test
    void test02() {
        open("http://92.51.36.108:7777/sl.qa");
        $("body").shouldHave(text("Промышленные приложения"));
        sleep(1000);
    }

    @Test
    void test03() {
        open("https://pogoda.e1.ru");
        $("body").shouldHave(text("Прогноз погоды на 10 дней"));
        sleep(1000);
    }

    @Test
    void test04() {
        open("https://pogoda.e1.ru");
        $("body").shouldHave(text("Прогноз погоды на 300 дней"));
        sleep(1000);
    }
}
