package com.example.Enotes.service;

import com.example.Enotes.dto.NotesDto;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto) throws Exception;

    public List<NotesDto> getAllNotes();
}
