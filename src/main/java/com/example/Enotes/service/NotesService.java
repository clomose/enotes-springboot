package com.example.Enotes.service;

import com.example.Enotes.dto.FavouriteNoteDto;
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

    public NotesResponse getAllNotesByUser(Integer pageNo,Integer pageSize);

    public NotesResponse getAllNotesBySearch(Integer pageNo,Integer pageSize,String key);

    public void softDeleteNotes(Integer id) throws Exception;

    public void restoreNotes(Integer id) throws Exception;

    public List<NotesDto> getUserRecycleBinNotes();

    public void hardDeleteNotes(Integer id) throws Exception;

    public void emptyRecycleBin() throws Exception;

    public void favouriteNotes(Integer noteId) throws Exception;

    public void unFavouriteNotes(Integer noteId) throws Exception;

    public List<FavouriteNoteDto> getUserFavouriteNotes() throws Exception;

    public Boolean copyNotes(Integer id) throws Exception;
}
