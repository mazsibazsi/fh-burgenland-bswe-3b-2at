package io.muehlbachler.fhburgenland.swm.examination.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.muehlbachler.fhburgenland.swm.examination.model.Person;

public interface PersonRepository extends CrudRepository<Person, String> {

    /**
     * Finds the {@link Person}s according to their first names.
     * @param firstName {@link String} The first name of a person.
     * @return A {@link List} of {@link Person}s with the first name given in the parameter. Can be empty.
     */
    List<Person> findByFirstName(String firstName);

    /**
     * Finds the {@link Person}s according to their last names.
     * @param lastName {@link String} The last name of a person.
     * @return A {@link List} of {@link Person}s with the last name given in the parameter. Can be empty.
     */
    List<Person> findByLastName(String lastName);

    /**
     * Finds the {@link Person}s according to their first and last names.
     * @param firstName {@link String} The first name of a person.
     * @param lastName {@link String} The last name of a person.
     * @return A {@link List} of {@link Person}s with the first and last name, given in the parameter. Can be empty.
     */
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
