package io.muehlbachler.fhburgenland.swm.examination.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.service.PersonService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.muehlbachler.fhburgenland.swm.examination.model.Person;

import java.util.List;

/**
 * Unit Tests for the PersonController.
 */
@SpringBootTest
public class PersonControllerTest {
    @Autowired
    private PersonController personController;

    @Mock
    private PersonService personService;

    /**
     * Test, if correct {@link Person} gets received by id.
     */
    @Test
    void testGetById() {
        ResponseEntity<Person> person =
                personController.get("81150016-8501-4b97-9168-01113e21d8a5");

        assertEquals(HttpStatus.OK, person.getStatusCode(), "person should be found");
        assertEquals("John", person.getBody().getFirstName(), "firstName should be John");
    }

    /**
     * Tests, if all {@link Person}s are received.
     */
    @Test
    void testList() {
        List<Person> persons = personController.list();

        assertEquals(2, persons.size(), "size should be 2");
        assertEquals("John", persons
                .stream()
                .filter((Person p) -> {
                    return p.getFirstName().equals("John");
                }).findFirst().get().getFirstName());
        assertEquals("Jane", persons
                .stream()
                .filter((Person p) -> {
                    return p.getFirstName().equals("Jane");
                }).findFirst().get().getFirstName());
    }


    /**
     * Submitting {@link Person} test.
     */
    @Test
    void testCreate() {
        Person p = new Person("1","Barry", "Allen", Lists.newArrayList());
        Mockito.when(personService.create(p)).thenReturn(p);
        Person g = personController.create(p);

        assertEquals(p.getId(), g.getId());
        assertEquals(p.getFirstName(), g.getFirstName());
        assertEquals(p.getLastName(), g.getLastName());
    }

    /**
     * Tests the query method with a last name, that should return exactly two persons.
     */
    @Test
    void testQuery() {
        List<Person> persons = personController.query("", "Doe");
        assertEquals(2, persons.size(), "size should be 2");
        assertEquals("John", persons
                .stream()
                .filter((Person p) -> {
                    return p.getFirstName().equals("John");
                }).findFirst().get().getFirstName());
        assertEquals("Jane", persons
                .stream()
                .filter((Person p) -> {
                    return p.getFirstName().equals("Jane");
                }).findFirst().get().getFirstName());

    }

    /**
     * Test to create a note for Jane Doe.
     */
    @Test
    void testCreateNote() {
        Note note = new Note(
                "1",
                personController.get("81150016-8501-4b97-9168-01113e21d8a5").getBody(),
                "asd");
        Note response = personController.createNote(
                "81150016-8501-4b97-9168-01113e21d8a5",
                note
        ).getBody();

        assertEquals(note.getId(), response.getId());
        assertEquals(note.getContent(), response.getContent());
        assertEquals(note.getPerson().getId(), response.getPerson().getId());
    }


}
