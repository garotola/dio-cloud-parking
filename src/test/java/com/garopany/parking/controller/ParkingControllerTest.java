package com.garopany.parking.controller;

import com.garopany.parking.controller.dto.ParkingCreateDTO;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest  {
    @LocalServerPort
    private int randomPort;
    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }
    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .auth()
                .basic("user", "Dio@12345")
                .when()
                .get("/parking")
                .then()
                .body("license[0]", Matchers.equalTo("WRT-5555"))
                .extract().response().body().prettyPrint();
    }

    @Test
    void whenCreateThenCheckIsCreated() {
        ParkingCreateDTO createDTO = new ParkingCreateDTO("WRT-5555", "PA", "BRASILIA", "ROSA");
        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking ")
                .then()
                .statusCode(201)
                .body("license", Matchers.equalTo("WRT-5555"));
    }
}