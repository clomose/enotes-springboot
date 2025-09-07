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

    public NotesResponse getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize);

    public void softDeleteNotes(Integer id) throws Exception;

    public void restoreNotes(Integer id) throws Exception;

    public List<NotesDto> getUserRecycleBinNotes(Integer useId);

    public void hardDeleteNotes(Integer id) throws Exception;

    public void emptyRecycleBin(Integer userId) throws Exception;

    public void favouriteNotes(Integer noteId) throws Exception;

    public void unFavouriteNotes(Integer noteId) throws Exception;

    public List<FavouriteNoteDto> getUserFavouriteNotes() throws Exception;
}
