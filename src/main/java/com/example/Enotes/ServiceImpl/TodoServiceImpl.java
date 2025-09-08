package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.TodoDto;
import com.example.Enotes.entity.Todo;
import com.example.Enotes.enums.TodoStatus;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.repository.TodoRepository;
import com.example.Enotes.service.TodoService;
import com.example.Enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception {
        // validate todo status
        validation.todoValidation(todoDto);

        Todo todo = mapper.map(todoDto,Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo save = todoRepository.save(todo);
        if (!ObjectUtils.isEmpty(save)){
            return true;
        }
        return false;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception {
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Todo not found! Invalid exception"));
        TodoDto todoDto = mapper.map(todo,TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    private void setStatus(TodoDto todoDto, Todo todo) {
        for(TodoStatus st : TodoStatus.values()){
            if(st.getId().equals(todo.getStatusId())){
                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
                        .id(st.getId())
                        .name(st.getName())
                        .build();
                todoDto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId = 2;
        List<Todo> todos = todoRepository.findByCreatedBy(userId);
        List<TodoDto> todoDtos = todos.stream().map((td) -> mapper.map(td,TodoDto.class)).toList();
        for(int i=0;i<todoDtos.size();i++){
            setStatus(todoDtos.get(i),todos.get(i));
        }
        return todoDtos;
    }
}
