package io.muehlbachler.fhburgenland.swm.examination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.repository.NoteRepository;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This class is a concrete implementation of the NoteService interface.
 */
@NoArgsConstructor
@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    /**
     * Returns the note from the repository.
     *
     * @inheritDocs
     */
    @Override
    public Optional<Note> get(String id) {
        return noteRepository.findById(id);
    }

    /**
     * Saves the {@link Note} to the repository.
     *
     * @inheritDocs
     */
    @Override
    public Note create(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Finds the {@link Note} from the repository,
     * that contains the queried text in the parameters.
     *
     * @inheritDocs
     */
    @Override
    public List<Note> queryByContent(String query) {
        return noteRepository.findByContentContaining(query);
    }
}
