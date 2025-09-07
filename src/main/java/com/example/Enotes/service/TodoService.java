package com.example.Enotes.service;

import com.example.Enotes.dto.TodoDto;

import java.util.List;

public interface TodoService {
    public Boolean saveTodo(TodoDto todo) throws Exception;
    public TodoDto getTodoById(Integer id) throws Exception;
    public List<TodoDto> getTodoByUser();
}
