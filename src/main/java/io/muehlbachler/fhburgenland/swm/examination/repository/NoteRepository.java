package io.muehlbachler.fhburgenland.swm.examination.repository;

import java.util.List;

import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import org.springframework.data.repository.CrudRepository;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;

/**
 * Representing the repository for persisting a {@link Person},
 * identified by a {@link String} ID.
 */
public interface NoteRepository extends CrudRepository<Note, String> {

    /**
     * Finds {@link Note}s, that contain the searched content from the parameter.
     *
     * @param content {@link String} The content to be looked for in the {@link Note}s
     * @return A {@link List} of {@link Note}s, that contain the content given in the param.
     *              Can be empty.
     */
    List<Note> findByContentContaining(String content);
}
