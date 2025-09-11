package com.example.Enotes.controller;

import com.example.Enotes.dto.FavouriteNoteDto;
import com.example.Enotes.dto.NotesDto;
import com.example.Enotes.dto.NotesResponse;
import com.example.Enotes.entity.FileDetails;
import com.example.Enotes.service.NotesService;
import com.example.Enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception{
        Boolean savedNotes = notesService.saveNotes(notes,file);
        if(savedNotes){
            return CommonUtil.createBuildResponseMessage("notes saved successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes Note saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{

        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] data = notesService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());

        return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notes  = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUSer(
            @RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize
    ){
        Integer userId = 2;
        NotesResponse notes  = notesService.getAllNotesByUser(userId,pageNo,pageSize);
        if(ObjectUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Restore Successfully",HttpStatus.OK);
    }

    @GetMapping("/recycle-bin/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes(){
        Integer useId=2;
        List<NotesDto> notes = notesService.getUserRecycleBinNotes(useId);
        if(CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuildResponseMessage("Notes not avaliable in Recycle Bin",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Deleted Successfully",HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin() throws Exception{
        Integer userId=2;
        notesService.emptyRecycleBin(userId);
        return CommonUtil.createBuildResponseMessage("Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception{
        notesService.favouriteNotes(noteId);
        return CommonUtil.createBuildResponseMessage("Notes Added Favourite",HttpStatus.CREATED);
    }

    @DeleteMapping("/un-fav/{favNotId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNotId) throws Exception{
        notesService.unFavouriteNotes(favNotId);
        return CommonUtil.createBuildResponseMessage("Remove Favourite",HttpStatus.OK);
    }

    @GetMapping("/fav-note")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserFavouriteNote() throws Exception{
        List<FavouriteNoteDto> notes = notesService.getUserFavouriteNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception{
        Boolean copyNotes = notesService.copyNotes(id);
        if (copyNotes){
            return CommonUtil.createBuildResponseMessage("Copied Success",HttpStatus.CREATED);
        }
        return CommonUtil.createBuildResponseMessage("Copied Failed! Try again",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
