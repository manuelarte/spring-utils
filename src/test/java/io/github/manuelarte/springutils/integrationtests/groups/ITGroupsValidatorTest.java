package io.github.manuelarte.springutils.integrationtests.groups;

import io.github.manuelarte.springutils.integrationtests.exampleapp.MyExampleApp;
import io.github.manuelarte.springutils.integrationtests.exampleapp.models.ExampleEntity;
import io.github.manuelarte.springutils.integrationtests.exampleapp.repositories.ExampleEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MyExampleApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITGroupsValidatorTest {

    @Autowired
    private ExampleEntityRepository repository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testNewMandatoryFieldNotPresent() {
        final ResponseEntity<ExampleEntity> response = this.restTemplate.postForEntity(String.format("http://localhost:%d/example-entity", port),
                new ExampleEntity(null, "M", null), ExampleEntity.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateMandatoryFieldNotPresent() {
        final Long id = 1L;
        final ResponseEntity<ExampleEntity> response = this.restTemplate.postForEntity(String.format("http://localhost:%d/example-entity/%d", port, id),
                new ExampleEntity(null, "M", null), ExampleEntity.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateNullFieldPresent() {
        final Long id = 1L;
        final ResponseEntity<ExampleEntity> response = this.restTemplate.postForEntity(String.format("http://localhost:%d/example-entity/%d", port, id),
                new ExampleEntity(id, "M", "D"), ExampleEntity.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testPartialUpdateNullFieldPresent() {
        final Long id = 1L;
        final ResponseEntity<ExampleEntity> response = this.restTemplate.exchange(String.format("http://localhost:%d/example-entity/%d", port, id),
                HttpMethod.PATCH, new HttpEntity<>(new ExampleEntity(id, "M", "D")), ExampleEntity.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testPartialUpdateOneFieldPresent() {
        final ExampleEntity saved = this.repository.save(new ExampleEntity(null, "M", "D"));
        final Long id = saved.getId();
        final ResponseEntity<ExampleEntity> response = this.restTemplate.exchange(String.format("http://localhost:%d/example-entity/%d", port, id),
                HttpMethod.PATCH, new HttpEntity<>(new ExampleEntity(null, null, "D")), ExampleEntity.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
