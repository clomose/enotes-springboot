package com.example.Enotes.endpoints;

import com.example.Enotes.dto.TodoDto;
import com.example.Enotes.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Todo", description = "All the Todo Operation APIs")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @Operation(summary = "Save Todo", tags = { "Notes" }, description = "Save Todo")
    @PostMapping("/")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;

    @Operation(summary = "Get Todo", tags = { "Notes" }, description = "Get Todo")
    @GetMapping("/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get All Todo By User", tags = { "Notes" }, description = "Get All Todo By User")
    @GetMapping("/list")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getAllTodoByUSer() throws Exception;
}
