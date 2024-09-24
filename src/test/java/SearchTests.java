import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTests {

    public void logIn () {
        open("https://school.qa.guru");
        $("[name=email]").setValue("zuloo@mail.ru");
        $("[name=password]").setValue("Ruslan12%");
        $("[id=xdget33092_1]").click();
    }

    public void logOut () {
        //open("https://school.qa.guru");
      //  $("[name=email]").setValue("zuloo@mail.ru");
      //  $("[name=password]").setValue("Ruslan12%");
        $("a[title='Профиль'] > .menu-item-icon").click();
        sleep(1000);
        $(".menu-item-logout > .subitem-link").click();
        sleep(1000);
    }



    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = false;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "chrome";
        Configuration.browserVersion = "100.0";
        Configuration.browserSize = "1920x1080";
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;
//        Configuration.baseUrl = "https://demoqa.com";
//        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void eachUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }


    @Test
    void successfulLoginTest() {
        open("https://school.qa.guru");
        $("[name=email]").setValue("zuloo@mail.ru");
        $("[name=password]").setValue("Ruslan12%");
        $("[id=xdget33092_1]").click();

        $(withText("Список тренингов")).should(Condition.exist);
        logOut();


        //  $("[class=page-header]").shouldHave(text("Список тренингов"));

    }


    @Test
    void negativeWrongPasswdTest() {
        open("https://school.qa.guru");
        $("[name=email]").setValue("zuloo@mail.ru");
        $("[name=password]").setValue("Ruslan12").pressEnter();
        $(".btn-error").shouldHave(text("Неверный пароль"));

    }

    @Test
    void negativeNoPasswdTest() {
        open("https://school.qa.guru");
        $("[name=email]").setValue("zuloo@mail.ru").pressEnter();
    //    $("[name=password]").setValue("Ruslan12").pressEnter();
        $(".btn-error").shouldHave(text("Не заполнено поле Пароль"));

    }

    @Test
    void negativeNoLoginTest() {
        open("https://school.qa.guru");
        //$("[name=email]").setValue("zuloo@mail.ru").pressEnter();
        $("[name=password]").setValue("Ruslan12").pressEnter();
        $(".btn-error").shouldHave(text("Не заполнено поле E-Mail"));

    }


    @Test
    void negativeWrongLoginFormateTest() {
        open("https://school.qa.guru");
        $("[name=email]").setValue("ффф@mail.ru");
        $("[name=password]").setValue("Ruslan12%").pressEnter();
        $(".btn-error").shouldHave(text("Неверный формат e-mail"));

    }


    @Test
    void negativeWrongLoginTest() {
        open("https://school.qa.guru");
        $("[name=email]").setValue("aaa@mail.ru");
        $("[name=password]").setValue("Ruslan12%").pressEnter();
        $(".btn-error").shouldHave(text("Неверный пароль"));

    }

    @Test
    void positiveSupport() {

    //    boolean w = true;
    //    assertTrue(w);
        logIn();
        $("a[title='Служба поддержки'] > .menu-item-icon").click();
        $(withText("Общение")).should(Condition.exist);
        $(withText("Новый разговор")).should(Condition.exist);
        logOut();

    }

    @Test
    void positivePurchase() {

        logIn();
        $("a[title='Покупки'] > .menu-item-icon").click();
        $(withText("Мои покупки")).should(Condition.exist);
        logOut();

    }


}
