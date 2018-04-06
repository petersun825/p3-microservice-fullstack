package com.example.cityapi.controller;

import com.example.cityapi.models.City;
import com.example.cityapi.repositories.CityRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public Iterable<City> findAllUsers() {
        return cityRepository.findAll();
    }

//    @GetMapping("/")
//    public Iterable<City> findAllCity() {
//        return cityRepository.findAll();
//    }

    @GetMapping("/{cityId}")
    public City findCityById(@PathVariable Long cityId) throws NotFoundException {

        City foundCity = cityRepository.findOne(cityId);

        if (foundCity == null) {
            throw new NotFoundException("City with ID of " + cityId + " was not found!");
        }

        return foundCity;
    }

    @DeleteMapping("/{cityId}")
    public HttpStatus deleteCityById(@PathVariable Long cityId) throws EmptyResultDataAccessException {
        cityRepository.delete(cityId);
        return HttpStatus.OK;
    }

    @PostMapping("/")
    public City createNewCity(@RequestBody City newCity) {

        return cityRepository.save(newCity);
    }

    @PatchMapping("/{cityId}")
    public City updateCityById(@PathVariable Long cityId, @RequestBody City cityRequest) throws NotFoundException {
        City cityFromDb = cityRepository.findOne(cityId);

        if (cityFromDb == null) {
            throw new NotFoundException("City with ID of " + cityId + " was not found!");
        }


        cityFromDb.setShort_title(cityRequest.getShort_title());
        cityFromDb.setAgency_name(cityRequest.getAgency_name());
        cityFromDb.setRequest_id(cityRequest.getRequest_id());
        cityFromDb.setSection_name(cityRequest.getSection_name());
        return cityRepository.save(cityFromDb);
    }

    @ExceptionHandler
    void handleCityNotFound(
            NotFoundException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler
    void handleDeleteNotFoundException(
            EmptyResultDataAccessException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}