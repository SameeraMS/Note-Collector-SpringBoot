package com.example.notecollecter.util;

import com.example.notecollecter.dto.impl.NoteDTO;
import com.example.notecollecter.dto.impl.UserDTO;
import com.example.notecollecter.entity.impl.NoteEntity;
import com.example.notecollecter.entity.impl.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //for user mapping
    public UserEntity toUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public UserDTO toUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public List<UserDTO> toUserDTOs(List<UserEntity> all) {
        return modelMapper.map(all, List.class);
    }

    //for note mapping
    public NoteEntity toNoteEntity(NoteDTO noteDTO) {
        return modelMapper.map(noteDTO, NoteEntity.class);
    }

    public NoteDTO toNoteDTO(NoteEntity noteEntity) {
        return modelMapper.map(noteEntity, NoteDTO.class);
    }

    public List<NoteDTO> toNoteDTOs(List<NoteEntity> all) {
        return modelMapper.map(all, List.class);
    }

}
