import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.text;

public class FindElementByClassTest {
    @Test
    void test01_find_element() {
        open("https://ru.wikipedia.org/wiki/Selenium");
        $(".mw-page-title-main").shouldHave(text("Selenium"));
        sleep(5_000);
    }

}
