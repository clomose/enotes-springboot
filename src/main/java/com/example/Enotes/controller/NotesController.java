package com.example.Enotes.controller;

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
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception{
        Boolean savedNotes = notesService.saveNotes(notes,file);
        if(savedNotes){
            return CommonUtil.createBuildResponseMessage("notes saved successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes Note saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/download/{id}")
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
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notes  = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @GetMapping("/user-notes")
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
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Restore Successfully",HttpStatus.OK);
    }

    @GetMapping("/recycle-bin/{id}")
    public ResponseEntity<?> getUserRecycleBinNotes(){
        Integer useId=2;
        List<NotesDto> notes = notesService.getUserRecycleBinNotes(useId);
        if(CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuildResponseMessage("Notes not avaliable in Recycle Bin",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }
}
