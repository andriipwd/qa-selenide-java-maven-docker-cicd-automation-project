
package com.example.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.pages.LoginPage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

class LoginTest {

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = Boolean.getBoolean("headless");

        open("https://www.saucedemo.com/");
    }

    @Test
    void standardUserCanLoginSuccessfully() {
        LoginPage loginPage = new LoginPage();

        loginPage.login("standard_user", "secret_sauce")
                .verifyProductsPageIsDisplayed();
    }

    @ParameterizedTest
    @CsvSource({
            "invalid_user, secret_sauce, 'Epic sadface: Username and password do not match any user in this service'",
            "standard_user, wrong_password, 'Epic sadface: Username and password do not match any user in this service'",
            "locked_out_user, secret_sauce, 'Epic sadface: Sorry, this user has been locked out.'"
    })
    void shouldDisplayErrorForUnsuccessfulLogin(
            String username,
            String password,
            String expectedError) {

        LoginPage loginPage = new LoginPage();

        loginPage.attemptLogin(username, password);

        loginPage.getErrorMessage()
                .shouldHave(text(expectedError));
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}