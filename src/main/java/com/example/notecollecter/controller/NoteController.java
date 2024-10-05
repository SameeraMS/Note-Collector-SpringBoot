package com.example.notecollecter.controller;

import com.example.notecollecter.customStatusCodes.SelectedNoteErrorStatus;
import com.example.notecollecter.dto.NoteStatus;
import com.example.notecollecter.dto.impl.NoteDTO;
import com.example.notecollecter.exception.DataPersistException;
import com.example.notecollecter.exception.NoteNotFoundException;
import com.example.notecollecter.service.NoteService;
import com.example.notecollecter.util.AppUtil;
import com.example.notecollecter.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody NoteDTO noteDTO) {
        try {
            String noteId = AppUtil.generateNoteId();
            noteDTO.setNoteId(noteId);

            noteService.saveNote(noteDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteStatus getSelectedNote(@PathVariable("noteId") String noteId) {
        if (!noteId.matches(Regex.NOTE_ID_REGEX)) {
            return new SelectedNoteErrorStatus(2, "Invalid Note Id");
        }
        return noteService.getSelectedNote(noteId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO> getAllNotes() {
        return noteService.getAllNotes();
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable("noteId") String noteId) {
        if (!noteId.matches(Regex.NOTE_ID_REGEX)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            noteService.deleteNote(noteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoteNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{noteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateNote(@PathVariable("noteId") String noteId, @RequestBody NoteDTO noteDTO) {
        try {
            noteDTO.setNoteId(noteId);
            noteService.updateNote(noteId, noteDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoteNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}