package com.example.Enotes.service;

import com.example.Enotes.dto.NotesDto;
import com.example.Enotes.dto.NotesResponse;
import com.example.Enotes.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes,MultipartFile file) throws Exception;

    public List<NotesDto> getAllNotes();

    public byte[] downloadFile(FileDetails fileDetails) throws Exception;

    public FileDetails getFileDetails(Integer id) throws Exception;

    public NotesResponse getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize);

    void softDeleteNotes(Integer id) throws Exception;

    void restoreNotes(Integer id) throws Exception;

    List<NotesDto> getUserRecycleBinNotes(Integer useId);
}
