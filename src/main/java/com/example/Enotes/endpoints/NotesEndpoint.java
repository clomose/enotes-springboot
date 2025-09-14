package com.example.Enotes.endpoints;

import com.example.Enotes.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/save")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @GetMapping("/download/{id}")
    @PreAuthorize(Constants.ANY_ROLE)
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @GetMapping("/")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize
    );

    @GetMapping("/search")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> searchNotes(
            @RequestParam(name="key",defaultValue = "") String key,
            @RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize
    );

    @GetMapping("/delete/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/restore/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/recycle-bin/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getUserRecycleBinNotes();

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/delete")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> emptyRecycleBin() throws Exception;

    @GetMapping("/fav/{noteId}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;

    @DeleteMapping("/un-fav/{favNotId}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNotId) throws Exception;

    @GetMapping("/fav-note")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getUserFavouriteNote() throws Exception;

    @GetMapping("/copy/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}
