package com.github.manuelarte.springutils.integrationtests.validators;

import com.github.manuelarte.springutils.integrationtests.exampleapp.MyExampleApp;
import com.github.manuelarte.springutils.integrationtests.exampleapp.models.ExampleEntity;
import com.github.manuelarte.springutils.integrationtests.exampleapp.repositories.ExampleEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MyExampleApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITExistsValidatorTest {

    @Autowired
    private ExampleEntityRepository repository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testExistsOnNotExistingPathVariable() {
        final Long id = 1L;
        final ResponseEntity<ExampleEntity> response = this.restTemplate.postForEntity(String.format("http://localhost:%d/example-entity/%d", port, id),
                new ExampleEntity(null, "M", "D"), ExampleEntity.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testExistsOnExistingPathVariable() {
        final ExampleEntity entity = this.repository.save(new ExampleEntity(null, "M", "D"));
        final Long id = entity.getId();
        final ResponseEntity<ExampleEntity> response = this.restTemplate.postForEntity(String.format("http://localhost:%d/example-entity/%d", port, id),
                new ExampleEntity(null, "M", "D"), ExampleEntity.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
