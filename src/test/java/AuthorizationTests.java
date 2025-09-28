import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

public class AuthorizationTests {
    @Test
    void test01_login_success() {
        open("https://www.saucedemo.com/");
        $("[data-test='username']").setValue("visual_user");
        sleep(1_000);
        $("[data-test='password']").setValue("secret_sauce");
        sleep(2_000);
        $("[data-test='login-button']").click();
        $("[data-test='primary-header']").shouldHave(text("Swag Labs"));
        $("[data-test='title']").shouldHave(text("Products"));
        sleep(3_000);
    }

    @Test
    void test02_login_wrong_password() {
        open("https://www.saucedemo.com/");
        $("[data-test='username']").setValue("visual_user");
        sleep(1_000);
        $("[data-test='password']").setValue("wrong_pwd");
        sleep(2_000);
        $("[data-test='login-button']").click();
        $(".login_container").shouldHave(text("Swag Labs"));
        $("[data-test='error']").shouldHave(text("Epic sadface: Username and password do not match any user in this service"));
        sleep(5_000);
    }
}
