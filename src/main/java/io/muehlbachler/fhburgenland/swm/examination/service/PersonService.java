package io.muehlbachler.fhburgenland.swm.examination.service;

import java.util.List;
import java.util.Optional;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.model.Person;

/**
 * This class represents the person service interface,
 * that describes what methods must be implemented by the service.
 */
public interface PersonService {

    /**
     * Returns all {@link Person}s from the repository.
     *
     * @return A {@link List} of {@link Person} instances. Can be empty.
     */
    List<Person> getAll();

    /**
     * Returns a {@link Person} based on their unique ID.
     *
     * @param id {@link String}
     * @return An {@link Optional} of a {@link Person}, which means the result can be null,
     *              if none is found.
     */
    Optional<Person> get(String id);

    /**
     * Creates a {@link Person} instance.
     *
     * @param person {@link Person}
     * @return The saved {@link Person} instance.
     */
    Person create(Person person);

    /**
     * Returns a list of {@link Person}s, that match one or both of the names.
     *
     * @param firstName {@link String} Corresponds to the firstName field of the Person.
     * @param lastName {@link String} Corresponds to the lastName field of the Person.
     * @return A {@link List} of {@link Person} instances. Can be empty.
     */
    List<Person> findByName(String firstName, String lastName);

    /**
     * Creates a {@link Note} instance and pairs it with the personId.
     *
     * @param personId {@link String}
     * @param note {@link Note}
     * @return An {@link Optional} of {@link Note}, that can be empty.
     */
    Optional<Note> createNote(String personId, Note note);
}
