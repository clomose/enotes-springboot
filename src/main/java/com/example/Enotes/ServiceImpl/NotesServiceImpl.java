package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.NotesDto;
import com.example.Enotes.entity.Notes;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.repository.CategoryRepository;
import com.example.Enotes.repository.NotesRepository;
import com.example.Enotes.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception {

        //category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notes = mapper.map(notesDto,Notes.class);
        Notes saveNotes = notesRepository.save(notes);
        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws Exception {
        categoryRepository.findById(category.getId()).orElseThrow(() ->
                new ResourceNotFoundException("category id invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream().map(notes ->
                mapper.map(notes,NotesDto.class)).toList();
    }
}
