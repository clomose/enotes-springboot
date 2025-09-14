package com.example.Enotes.controller;

import com.example.Enotes.dto.TodoDto;
import com.example.Enotes.endpoints.TodoEndpoint;
import com.example.Enotes.service.TodoService;
import com.example.Enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController implements TodoEndpoint {

    @Autowired
    private TodoService todoService;

    @Override
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception{
        Boolean saveTodo =  todoService.saveTodo(todoDto);
        if(saveTodo){
            return CommonUtil.createBuildResponseMessage("Todo Saved", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Todo Not Saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception{
        TodoDto todoById = todoService.getTodoById(id);
        return CommonUtil.createBuildResponse(todoById, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllTodoByUSer() throws Exception{
        List<TodoDto> todo = todoService.getTodoByUser();
        if(CollectionUtils.isEmpty(todo)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(todo, HttpStatus.OK);
    }
}
