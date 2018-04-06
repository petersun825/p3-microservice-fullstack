
package com.example.cityapi.repositories;

import com.example.cityapi.models.City;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableEurekaClient
@RestController
public interface CityRepository extends CrudRepository<City, Long> {

}
