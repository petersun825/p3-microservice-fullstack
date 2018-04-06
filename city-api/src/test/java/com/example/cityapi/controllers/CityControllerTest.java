package com.example.cityapi.controllers;

import com.example.cityapi.models.City;
import com.example.cityapi.repositories.CityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import org.springframework.http.MediaType;

@RunWith(SpringRunner.class)
@WebMvcTest(CitysController.class)
public class CitysControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonObjectMapper;

    private City newCity;

    private City updatedSecondCity;

    @MockBean
    private CityRepository mockCityRepository;


    @Test
    public void findAllCitys_success_returnsStatusOK() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllCitys_success_returnAllCitysAsJSON() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Before
    public void setUp() {
        City firstCity = new City(
                "org_name",
                "some primary",
                "some secondary",
                "1",
                "2"
        );

        City secondCity = new City(
                "second_org",
                "some other primary",
                "some other secondary",
                "1",
                "2"
        );

        newCity = new City(
                "new_city_for_create",
                "New",
                "City",
                "1",
                "2"
        );

        updatedSecondCity = new City(
                "updated_orgname",
                "Updated",
                "Info",
                "1",
                "2"
        );


        given(mockCityRepository.save(updatedSecondCity)).willReturn(updatedSecondCity);
        given(mockCityRepository.save(newCity)).willReturn(newCity);

        Iterable<City> mockCitys =
                Stream.of(firstCity, secondCity).collect( Collectors.toList());

        given(mockCityRepository.findAll()).willReturn(mockCitys);
        given(mockCityRepository.findOne(1L)).willReturn(firstCity);
        given(mockCityRepository.findOne(4L)).willReturn(null);

        doAnswer(invocation -> {
            throw new EmptyResultDataAccessException("ERROR MESSAGE FROM MOCK!!!", 1234);
        }).when(mockCityRepository).delete(4L);

    }

    @Test
    public void findAllCitys_success_returnOrgNameForEachCity() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].orgName", is("org_name")));
    }

    @Test
    public void findAllCitys_success_returnPrimaryNameForEachCity() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].primaryName", is("some primary")));
    }

    @Test
    public void findAllCitys_success_returnSecondaryNameForEachCity() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].secondaryName", is("some secondary")));
    }

    @Test
    public void findAllCitys_success_returnLatForEachCity() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].lat", is("1")));
    }

    @Test
    public void findAllCitys_success_returnLonForEachCity() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].lon", is("2")));
    }

    @Test
    public void findCityById_success_returnsStatusOK() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findCityById_success_returnOrgName() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.orgName", is("org_name")));
    }

    @Test
    public void findCityById_success_returnPrimaryName() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.primaryName", is("some primary")));
    }

    @Test
    public void findCityById_success_returnSecondaryName() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.secondaryName", is("some secondary")));
    }

    @Test
    public void findCityById_success_returnLat() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.lat", is("1")));
    }

    @Test
    public void findCityById_success_returnLon() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.lon", is("2")));
    }

    @Test
    public void findCityById_failure_cityNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(get("/4"))
                .andExpect(status().reason(containsString("City with ID of 4 was not found!")));
    }

    @Test
    public void deleteCityById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(delete("/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCityById_success_deletesViaRepository() throws Exception {

        this.mockMvc.perform(delete("/1"));

        verify(mockCityRepository, times(1)).delete(1L);
    }

    @Test
    public void deleteCityById_failure_cityNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(delete("/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCity_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newCity))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void createCity_success_returnsOrgName() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newCity))
                )
                .andExpect(jsonPath("$.orgName", is("new_city_for_create")));
    }

    @Test
    public void createCity_success_returnsPrimaryName() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newCity))
                )
                .andExpect(jsonPath("$.primaryName", is("New")));
    }

    @Test
    public void createCity_success_returnsSecondaryName() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newCity))
                )
                .andExpect(jsonPath("$.secondaryName", is("City")));
    }

    @Test
    public void createCity_success_returnsLat() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newCity))
                )
                .andExpect(jsonPath("$.lat", is("1")));
    }

    @Test
    public void createCity_success_returnsLon() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newCity))
                )
                .andExpect(jsonPath("$.lon", is("2")));
    }

    @Test
    public void updateCityById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondCity))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void updateCityById_success_returnsUpdatedOrgName() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondCity))
                )
                .andExpect(jsonPath("$.orgName", is("updated_orgname")));
    }

    @Test
    public void updateCityById_success_returnsUpdatedPrimaryName() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondCity))
                )
                .andExpect(jsonPath("$.primaryName", is("Updated")));
    }

    @Test
    public void updateCityById_success_returnsUpdatedSecondaryName() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondCity))
                )
                .andExpect(jsonPath("$.secondaryName", is("Info")));
    }

    @Test
    public void updateCityById_success_returnsUpdatedLat() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondCity))
                )
                .andExpect(jsonPath("$.lat", is("1")));
    }

    @Test
    public void updateCityById_success_returnsUpdatedLon() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondCity))
                )
                .andExpect(jsonPath("$.lon", is("2")));
    }

    //  Unhappy tests
    @Test
    public void updateCityById_failure_cityNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(
                        patch("/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondCity))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCityById_failure_cityNotFoundReturnsNotFoundErrorMessage() throws Exception {

        this.mockMvc
                .perform(
                        patch("/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondCity))
                )
                .andExpect(status().reason(containsString("City with ID of 4 was not found!")));
    }



}