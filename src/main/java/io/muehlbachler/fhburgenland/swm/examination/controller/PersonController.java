package io.muehlbachler.fhburgenland.swm.examination.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import io.muehlbachler.fhburgenland.swm.examination.service.PersonService;

/**
 * The controller, responsible for handling the API calls to /person.
 */
@RestController
@RequestMapping("person")
public class PersonController {

    /**
     * The service for persisting changes to the {@link Person}s.
     */
    @Autowired
    private PersonService personService;


    /**
     * Returns all {@link Person}s in the repository as a {@link List}.
     *
     * @return A {@link List} of {@link Person}s. The list can be empty.
     */
    @GetMapping("/")
    public List<Person> list() {
        return personService.getAll();
    }

    /**
     * Returns a certain {@link Person} in the repository that
     * matches the ID in the parameter.
     *
     * @param id {@link String} The unique ID of the person queried.
     *                         Comes from the path variable 'id' with the GET method.
     * @return A {@link List} of {@link Person}s. The list can be empty.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> get(@PathVariable String id) {
        return ResponseEntity.of(personService.get(id));
    }

    /**
     * Creates and saves a {@link Person} to the repository, that comes in through
     * the Body of a POST request.
     *
     * @param person {@link Person} Comes through the Body of a POST request.
     * @return Returns the created and saved {@link Person}.
     */
    @PostMapping("/")
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    /**
     * Finds the person based on the incoming
     * firstName and/or lastName from the parameters.
     *
     * @param firstName {@link String} The first name of a {@link Person}.
     *                                Comes in through the query string of a GET request.
     * @param lastName {@link String} The last name of a {@link Person}.
     *                               Comes in through the query string of a GET request.
     * @return A {@link List} of {@link Person}s. The list can be empty if no match is found.
     */
    @GetMapping("/query")
    public List<Person> query(@RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName) {
        return personService.findByName(firstName, lastName);
    }

    /**
     * Creates and saves a note under a {@link Person}'s unique ID.
     *
     * @param id The unique identifier of a {@link Person}.
     *           Comes from the path variable of the POST request.
     * @param note A {@link Note} that is received from the Body of the POST request.
     * @return A {@link ResponseEntity} that contains the saved {@link Note}.
     */
    @PostMapping("/{id}/note")
    public ResponseEntity<Note> createNote(@PathVariable String id, @RequestBody Note note) {
        return ResponseEntity.of(personService.createNote(id, note));
    }
}
