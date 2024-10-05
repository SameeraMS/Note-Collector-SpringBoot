package com.example.notecollecter.service.impl;

import com.example.notecollecter.customStatusCodes.SelectedNoteErrorStatus;
import com.example.notecollecter.dao.NoteDao;
import com.example.notecollecter.dto.NoteStatus;
import com.example.notecollecter.dto.impl.NoteDTO;
import com.example.notecollecter.entity.impl.NoteEntity;
import com.example.notecollecter.exception.DataPersistException;
import com.example.notecollecter.exception.NoteNotFoundException;
import com.example.notecollecter.service.NoteService;
import com.example.notecollecter.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class NoteServiceIMPL implements NoteService {
    @Autowired
    private NoteDao noteDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveNote(NoteDTO noteDTO) {
        NoteEntity savedNote = noteDao.save(mapping.toNoteEntity(noteDTO));
        if (savedNote == null) {
            throw new DataPersistException("Note not saved");
        }
    }

    @Override
    public NoteStatus getSelectedNote(String noteId) {
        Optional<NoteEntity> search = noteDao.findById(noteId);
        if (search.isPresent()) {
            NoteEntity note = noteDao.getReferenceById(noteId);
            return mapping.toNoteDTO(note);
        } else {
            return new SelectedNoteErrorStatus(2, "Note " + noteId + " not found");
        }
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return mapping.toNoteDTOs(noteDao.findAll());
    }

    @Override
    public void deleteNote(String noteId) {
        if (!noteDao.existsById(noteId)) {
            throw new NoteNotFoundException("Note " + noteId + " not found");
        } else {
            noteDao.deleteById(noteId);
        }
    }

    @Override
    public void updateNote(String noteId, NoteDTO noteDTO) {
        Optional<NoteEntity> search = noteDao.findById(noteId);
        if (search.isPresent()) {
            search.get().setNoteTitle(noteDTO.getNoteTitle());
            search.get().setNoteDesc(noteDTO.getNoteDesc());
            search.get().setCreatedDate(noteDTO.getCreatedDate());
            search.get().setPriorityLevel(noteDTO.getPriorityLevel());
        } else {
            throw new NoteNotFoundException("Note " + noteId + " not found");
        }
    }
}
