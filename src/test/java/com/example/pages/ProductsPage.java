
package com.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.exactText;

public class ProductsPage {

    private final SelenideElement cartBadge =
        $("[data-test='shopping-cart-badge']");
    
    private final SelenideElement pageTitle = $(".title");

    private final ElementsCollection products = $$(".inventory_item");

    private final ElementsCollection productPrices =
            $$(".inventory_item_price");

    private final SelenideElement sortDropdown =
            $("[data-test='product-sort-container']");

    private final SelenideElement backpackButton =
            $("[data-test='add-to-cart-sauce-labs-backpack']");

    private final SelenideElement cartLink = $(".shopping_cart_link");

    public void verifyProductsPageIsDisplayed() {
        pageTitle.shouldHave(text("Products"));
        products.shouldHave(sizeGreaterThan(0));
    }

    public List<String> getProductPrices() {
        return productPrices.texts();
    }

    public void sortByPriceLowToHigh() {
        sortDropdown.selectOptionByValue("lohi");
    }

    public void verifyPricesOrder(List<String> expectedPrices) {
        productPrices.shouldHave(exactTexts(expectedPrices));
    }

    public void addBackpackToCart() {
        backpackButton.click();
    }

    public CartPage openCart() {
        cartLink.click();
        return new CartPage();
    }

    public void verifyCartBadgeCount(String expectedCount) {
    cartBadge.shouldHave(exactText(expectedCount));
    }
}