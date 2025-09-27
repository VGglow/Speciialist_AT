import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;

public class TestLogin {
    @Test
    void test01_find_element() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").setValue("standard_user");
        sleep(1_000);
        $("#password").setValue("secret_sauce");
        sleep(3_000);
        $("#loginButton").click();
        sleep(5_000);
    }

}
