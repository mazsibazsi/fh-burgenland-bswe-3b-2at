package io.muehlbachler.fhburgenland.swm.examination.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;

/**
 * The controller, responsible for handling the API calls to /note.
 */
@RestController
@RequestMapping("note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    /**
     * Returns a response with a {@link Note}, that has the matching ID given in the
     * parameters.
     * @param id {@link String} The ID of the Note that is queried.
     *                         The parameter comes from the GET mappings' path variable.
     * @return A {@link Note} wrapped inside a {@link ResponseEntity}
     *              to be returned to the requesting client.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> get(@PathVariable String id) {
        return ResponseEntity.of(noteService.get(id));
    }

    /**
     * Returns a {@link List} of {@link Note}s, that contain the queried {@link String}.
     *
     * @param query {@link String} The queried text to be looked for in the notes.
     *                            Comes from the query string of the URI named 'query'
     * @return A {@link List} of {@link Note}s, that contain the queried text.
     *              Can be empty.
     */
    @GetMapping("/query")
    public List<Note> query(@RequestParam("query") String query) {
        return noteService.queryByContent(query);
    }
}
