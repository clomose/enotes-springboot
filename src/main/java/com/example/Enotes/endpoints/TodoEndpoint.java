package com.example.Enotes.endpoints;

import com.example.Enotes.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllTodoByUSer() throws Exception;
}
