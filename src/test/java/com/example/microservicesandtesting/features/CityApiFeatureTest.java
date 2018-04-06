package com.example.microservicesandtesting.features;

import com.example.microservicesandtesting.models.City;
import com.example.microservicesandtesting.repostitories.CityRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;


import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CitysApiFeatureTest {

    @Autowired
    private CityRepository cityRepository;

    @Before
    public void setUp() {
        cityRepository.deleteAll();
    }

    @After
    public void tearDown() {
        cityRepository.deleteAll();
    }

    @Test
    public void shouldAllowFullCrudForACity() throws Exception {
        City firstCity = new City(
                "Officer",
                "Fire Department",
                "41241",
                "somewhere"
        );

        City secondCity = new City(
                "Libarian",
                "Police Department",
                "13245",
                "somewhere else"
        );

        Stream.of(firstCity, secondCity)
                .forEach(city -> {
                    cityRepository.save(city);
                });

        when()
                .get("http://localhost:8080/citys/")
                .then()
                .statusCode(is(200))
                .and().body(containsString("someone"))
                .and().body(containsString("Else"));

        // Test creating a City
        City cityNotYetInDb = new City(
                "The man",
                "NYPD",
                "3231232",
                "Try me"
        );

        given()
                .contentType(JSON)
                .and().body(cityNotYetInDb)
                .when()
                .post("http://localhost:8080/citys")
                .then()
                .statusCode(is(200))
                .and().body(containsString("new_city"));

        // Test get all Citys
        when()
                .get("http://localhost:8080/citys/")
                .then()
                .statusCode(is(200))
                .and().body(containsString("someone"))
                .and().body(containsString("Else"))
                .and().body(containsString("Yet Created"));

        // Test finding one city by ID
        when()
                .get("http://localhost:8080/citys/" + secondCity.getId())
                .then()
                .statusCode(is(200))
                .and().body(containsString("Someone"))
                .and().body(containsString("Else"));

        // Test updating a city
        secondCity.setAgency_name("changed_name");

        given()
                .contentType(JSON)
                .and().body(secondCity)
                .when()
                .patch("http://localhost:8080/citys/" + secondCity.getId())
                .then()
                .statusCode(is(200))
                .and().body(containsString("changed_name"));

        // Test deleting a city
        when()
                .delete("http://localhost:8080/citys/" + secondCity.getId())
                .then()
                .statusCode(is(200));
    }

}
