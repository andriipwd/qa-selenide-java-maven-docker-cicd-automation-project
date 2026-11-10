
package com.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$;

public class CartPage {

    private final SelenideElement pageTitle = $(".title");

    private final ElementsCollection cartItems = $$(".cart_item");

    public void verifyCartPageIsDisplayed() {
        pageTitle.shouldHave(exactText("Your Cart"));
    }

    public void verifyCartContainsExactlyOneProduct() {
        cartItems.shouldHave(size(1));
    }

    public void verifyProductIsPresent(String productName) {
        findCartItem(productName).shouldBe(visible);
    }

    public void verifyProductQuantity(
            String productName, String expectedQuantity) {

        findCartItem(productName)
                .$(".cart_quantity")
                .shouldHave(exactText(expectedQuantity));
    }

    private SelenideElement findCartItem(String productName) {
        return cartItems.findBy(text(productName));
    }
}