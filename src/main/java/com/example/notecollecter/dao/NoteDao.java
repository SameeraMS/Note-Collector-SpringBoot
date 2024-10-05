package com.example.notecollecter.dao;

import com.example.notecollecter.entity.impl.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteDao extends JpaRepository<NoteEntity, String> {
}
