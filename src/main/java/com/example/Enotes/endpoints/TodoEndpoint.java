package com.example.Enotes.endpoints;

import com.example.Enotes.dto.TodoDto;
import com.example.Enotes.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping("/")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;

    @GetMapping("/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

    @GetMapping("/list")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getAllTodoByUSer() throws Exception;
}
