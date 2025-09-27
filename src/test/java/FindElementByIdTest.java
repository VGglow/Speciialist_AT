import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;

public class FindElementByIdTest {
    @Test
    void test01_find_element() {
        open("http://92.51.36.108:7777/sl.qa/exam_tickets/");
        $("#quantity_of_tickets").setValue("10");
        sleep(5_000);
    }

}
