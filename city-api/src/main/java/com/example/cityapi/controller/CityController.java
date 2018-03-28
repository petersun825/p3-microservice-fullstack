package com.example.cityapi.controllers;


import com.example.cityapi.models.User;
import com.example.cityapi.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.cityapi.models.city;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;

@RestController
public class CityController {

    @Autowired
    private CityController cityRepository;

    @GetMapping("/")
    public Iterable<User> findAllCity() {
        return cityRepository.findAll();
    }

    @GetMapping("/{cityId}")
    public User findUserById(@PathVariable Long cityId) throws NotFoundException {
        User foundCity = userRepository.findOne(cityId);
        if (foundCity == null) {
            throw new NotFoundException("City with ID of " + cityId + " was not found!");
        }

        return foundCity;
    }
//
//    @GetMapping("/{userId}/requests")
//    public ArrayList<String> findAllRequestsForUserId(@PathVariable long userId, @PathVariable String requestId) throws NotFoundException {
//        User foundUser = userRepository.findOne(userId);
//        if (foundUser == null) {
//            throw new NotFoundException("User with ID of " + userId + " was not found!");
//        }
//        return foundUser.getRequestIds();
//    }

    @DeleteMapping("/{cityId}")
    public HttpStatus deleteUserById(@PathVariable Long userId) throws EmptyResultDataAccessException {
        userRepository.delete(userId);
        return HttpStatus.OK;
    }

    @PostMapping("/")
    public User createNewCity(@RequestBody User newUser) {
        return cityRepository.save(newCity);
    }



    @ExceptionHandler
    void handleUserNotFound(
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