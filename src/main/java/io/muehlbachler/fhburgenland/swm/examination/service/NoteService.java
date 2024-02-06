package io.muehlbachler.fhburgenland.swm.examination.service;

import java.util.List;
import java.util.Optional;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;

public interface NoteService {

    /**
     * Returns a {@link Note} based on the given ID.
     * @param id {@link String} The ID of a Note.
     * @return An {@link Optional} of a {@link Note}. Can be null.
     */
    Optional<Note> get(String id);

    /**
     * Creates a {@link Note} instance, and returns it.
     * @param note {@link Note}
     * @return A {@link Note} instance.
     */
    Note create(Note note);

    /**
     * Returns a {@link List} of {@link Note}s, which has its contents matching the queried string.
     * @param query {@link String} A string of characters to be searched.
     * @return A {@link List} of {@link Note}s. Can be an empty list.
     */
    List<Note> queryByContent(String query);
}
