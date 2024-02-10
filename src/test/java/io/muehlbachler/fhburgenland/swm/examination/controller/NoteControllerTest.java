package io.muehlbachler.fhburgenland.swm.examination.controller;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Tests for the NoteController.
 */
@SpringBootTest
public class NoteControllerTest {
    @Autowired
    private NoteController noteController;

    @Mock
    private NoteService noteService;

    /**
     * Successful retrieval by ID.
     */
    @Test
    void testGet() {
        ResponseEntity<Note> note = noteController.get("c5b38625-7eed-4705-858d-c685f18ed47d");
        assertEquals(HttpStatus.OK, note.getStatusCode(), "note should be found");
        assertEquals("Note 1", note.getBody().getContent(),
                "content should be Note 1");
    }

    /**
     * Unsuccessful retrieval by ID.
     */
    @Test
    void testGetNotFound() {
        ResponseEntity<Note> note = noteController.get("1");
        assertEquals(HttpStatus.NOT_FOUND, note.getStatusCode(), "note should not be found");
    }

    /**
     * Successful search by content retrieval.
     */
    @Test
    void testQuery() {
        List<Note> notes = noteController.query("Note 1");

        assertEquals(1, notes.size(), "size should be 1");
        assertEquals("Note 1", notes.get(0).getContent());
    }

    /**
     * Unsuccessful search by content retrieval.
     */
    @Test
    void testQueryNotFound() {
        List<Note> notes = noteController.query("asd");

        assertEquals(0, notes.size(), "size should be 0");
    }
}
