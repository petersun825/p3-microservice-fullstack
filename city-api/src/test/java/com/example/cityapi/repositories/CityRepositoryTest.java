package com.example.citysapi.repositories;

import com.example.citysapi.models.City;
import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CityRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @Before
    public void setUp() {
        City firstCity = new City(
                "org_name",
                "some date",
                "some time",
                "1",
                "2"
        );

        City secondCity = new City(
                "second_org",
                "some other date",
                "some other time",
                "1",
                "2"
        );

        entityManager.persist(firstCity);
        entityManager.persist(secondCity);
        entityManager.flush();
    }

    @Test
    public void findAll_returnsAllCity() {
        Iterable<City> CityFromDb = cityRepository.findAll();

        assertThat(Iterables.size(cityFromDb), is(2));
    }

    @Test
    public void findAll_returnsOrgName() {
        Iterable<City> cityFromDb = cityRepository.findAll();

        String secondCityOrgName = Iterables.get(cityFromDb, 1).getOrgName();

        assertThat(secondCityOrgName, is("second_org"));
    }

    @Test
    public void findAll_returnsPrimaryName() {
        Iterable<City> cityFromDb = cityRepository.findAll();

        String secondCityPrimaryName = Iterables.get(cityFromDb, 1).getPrimaryName();

        assertThat(secondCityPrimaryName, is("some date"));
    }

    @Test
    public void findAll_returnsSecondaryName() {
        Iterable<City> cityFromDb = cityRepository.findAll();

        String secondCitySecondaryName = Iterables.get(cityFromDb, 1).getSecondaryName();

        assertThat(secondCitySecondaryName, is("some other date"));
    }

}