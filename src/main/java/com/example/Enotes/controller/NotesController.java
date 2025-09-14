package com.example.Enotes.controller;

import com.example.Enotes.dto.FavouriteNoteDto;
import com.example.Enotes.dto.NotesDto;
import com.example.Enotes.dto.NotesResponse;
import com.example.Enotes.endpoints.NotesEndpoint;
import com.example.Enotes.entity.FileDetails;
import com.example.Enotes.service.NotesService;
import com.example.Enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NotesController implements NotesEndpoint {

    @Autowired
    private NotesService notesService;

    @Override
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception{
        Boolean savedNotes = notesService.saveNotes(notes,file);
        if(savedNotes){
            return CommonUtil.createBuildResponseMessage("notes saved successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes Note saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{

        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] data = notesService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());

        return ResponseEntity.ok().headers(headers).body(data);
    }

    @Override
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notes  = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize
    ){
        NotesResponse notes  = notesService.getAllNotesByUser(pageNo,pageSize);
        if(ObjectUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchNotes(
            @RequestParam(name="key",defaultValue = "") String key,
            @RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize
    ){
        NotesResponse notes  = notesService.getAllNotesBySearch(pageNo,pageSize,key);
        if(ObjectUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Deleted Successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Restore Successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserRecycleBinNotes(){
        List<NotesDto> notes = notesService.getUserRecycleBinNotes();
        if(CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuildResponseMessage("Notes not avaliable in Recycle Bin",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Deleted Successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> emptyRecycleBin() throws Exception{
        notesService.emptyRecycleBin();
        return CommonUtil.createBuildResponseMessage("Deleted Successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception{
        notesService.favouriteNotes(noteId);
        return CommonUtil.createBuildResponseMessage("Notes Added Favourite",HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNotId) throws Exception{
        notesService.unFavouriteNotes(favNotId);
        return CommonUtil.createBuildResponseMessage("Remove Favourite",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserFavouriteNote() throws Exception{
        List<FavouriteNoteDto> notes = notesService.getUserFavouriteNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception{
        Boolean copyNotes = notesService.copyNotes(id);
        if (copyNotes){
            return CommonUtil.createBuildResponseMessage("Copied Success",HttpStatus.CREATED);
        }
        return CommonUtil.createBuildResponseMessage("Copied Failed! Try again",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
