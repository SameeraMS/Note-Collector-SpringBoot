package com.example.notecollecter.customStatusCodes;

import com.example.notecollecter.dto.NoteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedNoteErrorStatus implements NoteStatus {
    private int statusCode;
    private String statusMessage;
}
