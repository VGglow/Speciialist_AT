import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BrowserPropertiesTests {
    @Test
    void test_browser_properties() {
        Configuration.browser = "edge";
        Configuration.browserSize = "1280x820";
        Configuration.browserPosition = "160x240";
        open("https://ok.ru");
        getWebDriver().manage().window().maximize();
        sleep(3_000);
    }
}
