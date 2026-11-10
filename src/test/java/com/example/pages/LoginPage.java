
package com.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement usernameInput =
            $("[data-test='username']");

    private final SelenideElement passwordInput =
            $("[data-test='password']");

    private final SelenideElement loginButton =
            $("[data-test='login-button']");

    private final SelenideElement errorMessage =
            $("[data-test='error']");

    public ProductsPage login(String username, String password) {
        enterCredentials(username, password);
        return new ProductsPage();
    }

    public void attemptLogin(String username, String password) {
        enterCredentials(username, password);
    }

    private void enterCredentials(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
    }

    public SelenideElement getErrorMessage() {
        return errorMessage;
    }
}