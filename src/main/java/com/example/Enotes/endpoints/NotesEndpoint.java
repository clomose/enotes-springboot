package com.example.Enotes.endpoints;

import com.example.Enotes.dto.NotesDto;
import com.example.Enotes.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Notes", description = "All the Notes Operation APIs")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @Operation(summary = "Save Notes", tags = { "Notes", "User" }, description = "User Save Notes")
    @PostMapping(value = "/save",consumes = "multipart/form-data")
    @PreAuthorize(Constants.ANY_ROLE)
    public ResponseEntity<?> saveNotes(@RequestParam @Parameter(description = "Json String Notes",required = true,content = @Content(schema = @Schema(implementation = NotesDto.class))) String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @Operation(summary = "Download Upload File", tags = { "Notes", "User" }, description = "Download file ")
    @GetMapping("/download/{id}")
    @PreAuthorize(Constants.ANY_ROLE)
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get All Notes", tags = { "Notes" }, description = "Get All Notes Admin")
    @GetMapping("/")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();

    @Operation(summary = "Get All notes For User", tags = { "Notes", "User" }, description = "Get All notes For User")
    @GetMapping("/user-notes")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize
    );

    @Operation(summary = "Search Notes", tags = { "Notes", "User" }, description = "User Search Notes")
    @GetMapping("/search")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> searchNotes(
            @RequestParam(name="key",defaultValue = "") String key,
            @RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize
    );

    @Operation(summary = "Delete Notes", tags = { "Notes", "User" }, description = "Delete Notes By user")
    @GetMapping("/delete/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Restore Delete Notes", tags = { "Notes", "User" }, description = "Restore Delete Notes from Recycle Bin")
    @GetMapping("/restore/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get Notes From Recycle Bin", tags = { "Notes", "User" }, description = "Get Notes From Recycle Bin")
    @GetMapping("/recycle-bin/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getUserRecycleBinNotes();

    @Operation(summary = "Hard Delete Notes", tags = { "Notes", "User" }, description = "Hard Delete Notes")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Empty User Recycle Bin", tags = { "Notes", "User" }, description = "Empty User Recycle Bin")
    @DeleteMapping("/delete")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> emptyRecycleBin() throws Exception;

    @Operation(summary = "Favorite Note", tags = { "Notes", "User" }, description = "User favorite notes")
    @GetMapping("/fav/{noteId}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;

    @Operation(summary = "UnFavoriteNote", tags = { "Notes", "User" }, description = "User UnFavorite Notes")
    @DeleteMapping("/un-fav/{favNotId}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNotId) throws Exception;

    @Operation(summary = "Get User Favorite Notes", tags = { "Notes", "User" }, description = "User Favorite Notes")
    @GetMapping("/fav-note")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getUserFavouriteNote() throws Exception;

    @Operation(summary = "Copy Notes", tags = { "Notes", "User" }, description = "Copy Notes")
    @GetMapping("/copy/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}
