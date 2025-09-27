import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;

public class SimpleWikiTest {
    @Test
    void test01_success() {
        open("https://ru.wikipedia.org/wiki/Selenium");
        $("body").shouldHave(text("WebDriver"));
    }

    @Test
    void test02_error() {
        open("https://ru.wikipedia.org/wiki/Selenium");
        $("body").shouldHave(text("Music"));
    }
}
