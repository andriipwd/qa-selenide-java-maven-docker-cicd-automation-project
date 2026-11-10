package com.example.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.pages.CartPage;
import com.example.pages.LoginPage;
import com.example.pages.ProductsPage;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

@Epic("SauceDemo UI")
@Feature("Product and Cart")
class ProductCartTest {

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = Boolean.getBoolean("headless");

        open("https://www.saucedemo.com/");
    }

    @Test
    @Story("Sort products by price and add product to cart")
    void userCanSortProductsAndAddProductToCart() {

        // Login
        ProductsPage productsPage = new LoginPage()
                .login("standard_user", "secret_sauce");

        productsPage.verifyProductsPageIsDisplayed();

        // Prepare expected prices in ascending order
        List<String> expectedPrices = productsPage.getProductPrices()
                .stream()
                .sorted(Comparator.comparing(
                        price -> new BigDecimal(price.replace("$", ""))))
                .toList();

        // Apply sorting and verify the result
        productsPage.sortByPriceLowToHigh();
        productsPage.verifyPricesOrder(expectedPrices);

        // Add product to cart
        productsPage.addBackpackToCart();

        // Verify cart badge
        productsPage.verifyCartBadgeCount("1");

        // Open cart
        CartPage cartPage = productsPage.openCart();

        // Verify cart contents
        cartPage.verifyCartPageIsDisplayed();
        cartPage.verifyCartContainsExactlyOneProduct();
        cartPage.verifyProductIsPresent("Sauce Labs Backpack");
        cartPage.verifyProductQuantity("Sauce Labs Backpack", "1");
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}