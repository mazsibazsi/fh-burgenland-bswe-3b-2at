package io.muehlbachler.fhburgenland.swm.examination.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import io.muehlbachler.fhburgenland.swm.examination.repository.PersonRepository;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;
import io.muehlbachler.fhburgenland.swm.examination.service.PersonService;

import javax.swing.text.html.Option;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    @Mock
    private NoteService noteService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl(personRepository, noteService);
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(personRepository);
        Mockito.verifyNoMoreInteractions(noteService);
    }

    /**
     * This test case tests the get function of the person service with a person found.
     * Expected to return with the person John Doe ID1.
     */
    @Test
    void testGetById() {
        Mockito.when(personRepository.findById("1"))
                .thenReturn(Optional.of(new Person("1", "John", "Doe", Lists.newArrayList())));

        Optional<Person> person = personService.get("1");

        assertTrue(person.isPresent());
        assertEquals("John", person.get().getFirstName(), "firstName should be John");
        assertEquals("Doe", person.get().getLastName(), "lastName should be Doe");

        Mockito.verify(personRepository, times(1)).findById("1");
    }

    /**
     * This test case tests the get function of the person service with a no person found.
     * Expected to return with an empty {@link Optional}.
     */
    @Test
    void testGetByIdNull() {
        Mockito.when(personRepository.findById("1"))
                .thenReturn(Optional.empty());

        Optional<Person> person = personService.get("1");

        assertFalse(person.isPresent());
        Mockito.verify(personRepository, times(1)).findById("1");
    }

    /**
     * This test case tests the create person function of the service.
     * Expected to return with the saved person.
     */
    @Test
    void testCreatePerson() {
        Person person = new Person("2", "Jane", "Doe", Lists.newArrayList());
        Mockito.when(personRepository.save(person)).thenReturn(person);

        Person returnedPerson = personService.create(person);
        assertEquals(returnedPerson.getFirstName(), person.getFirstName(), "firstName should be Jane");
        assertEquals(returnedPerson.getLastName(), person.getLastName(), "lastName should be Doe");
        Mockito.verify(personRepository, times(1)).save(person);
    }

    /**
     * This test case tests the findByName function with only the first name.
     * Expected to return a list with a single element, containing the person.
     */
    @Test
    void testFindByFirstName() {
        Person marySue = new Person("3", "Mary", "Sue", Lists.newArrayList());
        Mockito.when(personRepository.findByFirstName("Mary"))
                .thenReturn(
                        List.of(marySue)
                );

        List<Person> persons = personService.findByName("Mary", "");

        assertEquals(1, persons.size());
        assertEquals(List.of(marySue), persons);
        Mockito.verify(personRepository, times(1)).findByFirstName("Mary");
    }

    /**
     * This test case tests the findByName function with only the last name.
     * Expected to return a list with a single element, containing the person.
     */
    @Test
    void testFindByLastName() {
        Person marySue = new Person("3", "Mary", "Sue", Lists.newArrayList());
        Mockito.when(personRepository.findByLastName("Sue"))
                .thenReturn(
                        List.of(marySue)
                );

        List<Person> persons = personService.findByName("", "Sue");

        assertEquals(1, persons.size());
        assertEquals(List.of(marySue), persons);
        Mockito.verify(personRepository, times(1)).findByLastName("Sue");
    }

    /**
     * This test case tests the findByName function with both names given.
     * Expected to return a list with a single element, containing the person.
     */
    @Test
    void testFindByFirstAndLastName() {
        Person marySue = new Person("3", "Mary", "Sue", Lists.newArrayList());
        Mockito.when(personRepository.findByFirstNameAndLastName("Mary","Sue"))
                .thenReturn(
                        List.of(marySue)
                );

        List<Person> persons = personService.findByName("Mary", "Sue");

        assertEquals(1, persons.size());
        assertEquals(List.of(marySue), persons);
        Mockito.verify(personRepository, times(1)).findByFirstNameAndLastName("Mary", "Sue");
    }

    /**
     * This test case tests the findByName function with no name given.
     * Expected to return an empty list.
     */
    @Test
    void testFindByNoName() {
        Person marySue = new Person("3", "Mary", "Sue", Lists.newArrayList());
        Mockito.when(personRepository.findByFirstNameAndLastName("",""))
                .thenReturn(
                        List.of()
                );

        List<Person> persons = personService.findByName("", "");
        System.out.println(persons);
        assertEquals(0, persons.size());
        assertEquals(List.of(), persons);
        Mockito.verify(personRepository, times(1)).findByFirstNameAndLastName("", "");
    }

    /**
     * This test case tests for the successful creation of a note, assigned to a person.
     * Expected to return a note, with a person.
     */
    @Test
    void testCreateNote() {
        Person barryAllen = new Person("4", "Barry", "Allen", Lists.newArrayList());
        Note note = new Note("1", null, "asd");

        Mockito.when(personRepository.findById("4"))
                .thenReturn(
                        Optional.of(barryAllen)
                );
        Mockito.when(noteService.create(note)).thenReturn(note);

        Optional<Note> returnedNote = personService.createNote(barryAllen.getId(), note);

        assertTrue(returnedNote.isPresent());
        assertEquals(note, returnedNote.get());
        assertEquals(barryAllen, returnedNote.get().getPerson());
        Mockito.verify(personRepository, times(1)).findById("4");
        Mockito.verify(noteService, times(1)).create(note);
    }

    /**
     * This test case test the getAll function of the service.
     * Expected to return a list of four persons.
     */
    @Test
    void testGetAll() {
        Person johnDoe = new Person("1", "John", "Doe", Lists.newArrayList());
        Person janeDoe = new Person("2", "Jane", "Doe", Lists.newArrayList());
        Person marySue = new Person("3", "Mary", "Sue", Lists.newArrayList());
        Person barryAllen = new Person("4", "Barry", "Allen", Lists.newArrayList());

        Mockito.when((personRepository.findAll())).thenReturn(List.of(johnDoe, janeDoe, marySue, barryAllen));

        List<Person> persons = personService.getAll();

        assertEquals(4, persons.size());
        assertEquals(List.of(johnDoe, janeDoe, marySue, barryAllen), persons);
        Mockito.verify(personRepository, times(1)).findAll();
    }
}
