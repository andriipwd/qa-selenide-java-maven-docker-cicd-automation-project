
package com.example.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingApiTest {

    private static final String BASE_URL =
            "https://restful-booker.herokuapp.com";


        @Test
        void shouldReturn404WhenBookingDoesNotExist() {
                given()
                        .baseUri(BASE_URL)
                        .accept("application/json")
                        .pathParam("id", 999999999)
                .when()
                        .get("/booking/{id}")
                .then()
                        .statusCode(404);
        }

        @Test
        void shouldCreateBookingAndRetrieveItById() {

        // Prepare booking data
        Map<String, String> bookingDates = Map.of(
                "checkin", "2027-01-10",
                "checkout", "2027-01-12"
        );

        Map<String, Object> booking = Map.of(
                "firstname", "John",
                "lastname", "Smith",
                "totalprice", 250,
                "depositpaid", true,
                "bookingdates", bookingDates,
                "additionalneeds", "Breakfast"
        );

        // Create a booking
        Response createResponse = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept("application/json")
                .body(booking)
            .when()
                .post("/booking")
            .then()
                .statusCode(200)
                .extract()
                .response();

        // Extract and validate booking ID
        int bookingId = createResponse.jsonPath()
                .getInt("bookingid");

        assertTrue(bookingId > 0, "Booking ID should be positive");

        // Retrieve the created booking
        Response getResponse = given()
                .baseUri(BASE_URL)
                .accept("application/json")
                .pathParam("id", bookingId)
            .when()
                .get("/booking/{id}")
            .then()
                .statusCode(200)
                .extract()
                .response();

        // Verify retrieved booking data
        assertEquals("John",
                getResponse.jsonPath().getString("firstname"));

        assertEquals("Smith",
                getResponse.jsonPath().getString("lastname"));

        assertEquals(250,
                getResponse.jsonPath().getInt("totalprice"));

        assertTrue(
                getResponse.jsonPath().getBoolean("depositpaid"));

        assertEquals("2027-01-10",
                getResponse.jsonPath().getString("bookingdates.checkin"));

        assertEquals("2027-01-12",
                getResponse.jsonPath().getString("bookingdates.checkout"));
    }
}