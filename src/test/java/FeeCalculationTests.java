import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class FeeCalculationTests {
    @Test
    void test01_find_element() {
        open("http://92.51.36.108:7777/sl.qa/fc/v01/");
        $("[name=sum]").sendKeys("100");
        sleep(5_000);
    }
}
