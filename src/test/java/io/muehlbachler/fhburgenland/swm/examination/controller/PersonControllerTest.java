package io.muehlbachler.fhburgenland.swm.examination.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.muehlbachler.fhburgenland.swm.examination.model.Person;

/**
 * Unit Tests for the PersonController.
 */
@SpringBootTest
public class PersonControllerTest {
    @Autowired
    private PersonController personController;

    @Test
    void testGetById() {
        ResponseEntity<Person> person =
                personController.get("81150016-8501-4b97-9168-01113e21d8a5");

        assertEquals(HttpStatus.OK, person.getStatusCode(), "person should be found");
        assertEquals("John", person.getBody().getFirstName(), "firstName should be John");
    }
}
