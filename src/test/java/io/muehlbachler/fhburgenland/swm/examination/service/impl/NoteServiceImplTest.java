package io.muehlbachler.fhburgenland.swm.examination.service.impl;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import io.muehlbachler.fhburgenland.swm.examination.repository.NoteRepository;
import io.muehlbachler.fhburgenland.swm.examination.repository.PersonRepository;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

/**
 * Unit tests for the implementation of the NoteService.
 */
@ExtendWith(MockitoExtension.class)
public class NoteServiceImplTest {
    @Mock
    private NoteService noteService;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        noteService = new NoteServiceImpl(noteRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(noteRepository);
        Mockito.verifyNoMoreInteractions(personRepository);
    }

    /**
     * Tests successful retrieving by id.
     */
    @Test
    void testGetById() {
        Mockito.when(noteRepository.findById("1"))
               .thenReturn(
                       Optional.of(new Note("1", new Person(), "asd"))
               );

        Optional<Note> note = noteService.get("1");

        assertTrue(note.isPresent());
        assertEquals("1", note.get().getId(),
                "id should be 1");
        assertEquals("asd", note.get().getContent(),
                "content should be asd");
        Mockito.verify(noteRepository, times(1)).findById("1");
    }

    /**
     * Tests unsuccessful retrieving by id.
     */
    @Test
    void testGetByIdEmpty() {
        Mockito.when(noteRepository.findById("0"))
                .thenReturn(
                        Optional.empty()
                );

        Optional<Note> note = noteService.get("0");

        assertFalse(note.isPresent());
        Mockito.verify(noteRepository, times(1)).findById("0");
    }

    /**
     * Tests creating a note and persisting it.
     */
    @Test
    void testCreate() {
        Note note = new Note("1", new Person(), "asd");
        Mockito.when(noteRepository.save(note))
               .thenReturn(note);

        Note createdNote = noteService.create(note);

        assertEquals("1", createdNote.getId(),
                "id should be 1");
        assertEquals("asd", createdNote.getContent(),
                "content should be asd");
        Mockito.verify(noteRepository, times(1)).save(note);
    }

    /**
     * Searches for a note that contains a string,
     * then returns it.
     */
    @Test
    void testQueryByContent() {
        Note note = new Note("1", new Person(), "asd");
        Mockito.when(noteRepository.findByContentContaining("asd"))
               .thenReturn(List.of(note));

        List<Note> notes = noteService.queryByContent("asd");

        assertEquals(1, notes.size(),
                "list of size 1 expected");
        assertEquals(List.of(note), notes,
                "expected and received notes do not match");
        Mockito.verify(noteRepository, times(1))
               .findByContentContaining("asd");
    }

    /**
     * Does not find a note, matching the query.
     */
    @Test
    void testQueryByContentNotFound() {
        Mockito.when(noteRepository.findByContentContaining("fgh"))
               .thenReturn(List.of());

        List<Note> notes = noteService.queryByContent("fgh");

        assertEquals(0, notes.size(),
                "empty list expected");
        assertEquals(List.of(), notes,
                "expected and received notes do not match");
        Mockito.verify(noteRepository, times(1))
               .findByContentContaining("fgh");
    }

}
