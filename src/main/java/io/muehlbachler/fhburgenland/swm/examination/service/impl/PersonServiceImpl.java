package io.muehlbachler.fhburgenland.swm.examination.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import io.muehlbachler.fhburgenland.swm.examination.repository.PersonRepository;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;
import io.muehlbachler.fhburgenland.swm.examination.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This class is a concrete implementation of the PersonService interface.
 */
@NoArgsConstructor
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    /**
     * The repository, that persists the changes in the database.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * The service, which handles the notes.
     */
    @Autowired
    private NoteService noteService;

    /**
     * Returns a new {@link ArrayList} of {@link Person}s from the repo.
     *
     * @inheritDoc
     * @return An {@link ArrayList} that contains all {@link Person}s.
     */
    public List<Person> getAll() {
        return Lists.newArrayList(personRepository.findAll());
    }

    /**
     * Returns optionally a {@link Person} from the repo, if it exists.
     *
     * @inheritDoc
     * @return An {@link Optional} that potentially contains a single {@link Person} instance.
     */
    public Optional<Person> get(String id) {
        return personRepository.findById(id);
    }

    /**
     * Creates and persists a {@link Person} into the repository.
     *
     * @inheritDoc
     * @return An {@link Optional} that potentially contains a single {@link Person} instance.
     */
    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    /**
     * It returns a {@link List}, if either the firstName or lastName is given.
     * Given names, it returns a {@link List} of persons with matching names.
     * If no person is found, it returns an empty list.
     *
     * @inheritDoc
     */
    @Override
    public List<Person> findByName(String firstName, String lastName) {
        if (!firstName.isEmpty() && lastName.isEmpty()) {
            return personRepository.findByFirstName(firstName);
        } else if (!lastName.isEmpty() && firstName.isEmpty()) {
            return personRepository.findByLastName(lastName);
        }
        return personRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Gets the {@link Person} and sets the person field of {@link Note} to said Person.
     *
     * @inheritDoc
     */
    @Override
    public Optional<Note> createNote(String personId, Note note) {
        return get(personId).map((Person person) -> {
            note.setPerson(person);
            return noteService.create(note);
        });
    }
}
