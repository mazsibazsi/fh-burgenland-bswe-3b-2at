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

@NoArgsConstructor
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private NoteService noteService;

    /**
     * @inheritDoc
     * @return An {@link ArrayList} that contains all {@link Person}s.
     */
    public List<Person> getAll() {
        return Lists.newArrayList(personRepository.findAll());
    }

    /**
     * @inheritDoc
     * @return An {@link Optional} that potentially contains a single {@link Person} instance.
     */
    public Optional<Person> get(String id) {
        return personRepository.findById(id);
    }

    /**
     * @inheritDoc
     * Saves the {@link Person} to the repository.
     * @return An {@link Optional} that potentially contains a single {@link Person} instance.
     */
    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    /**
     * @inheritDoc
     * It returns only, if either the firstName or lastName is given.
     * Otherwise, it returns a new empty {@link ArrayList}
     */
    @Override
    public List<Person> findByName(String firstName, String lastName) {
        if (firstName.isEmpty() && !lastName.isEmpty()) {
            return personRepository.findByFirstName(lastName);
        } else if (lastName.isEmpty() && !firstName.isEmpty()) {
            return personRepository.findByLastName(firstName);
        }
        return Lists.newArrayList();
    }

    /**
     * @inheritDoc
     * Gets the {@link Person} and sets the person field of {@link Note} to said Person.
     */
    @Override
    public Optional<Note> createNote(String personId, Note note) {
        return get(personId).map((Person person) -> {
            note.setPerson(person);
            return noteService.create(note);
        });
    }
}
