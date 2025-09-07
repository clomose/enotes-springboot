package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.NotesDto;
import com.example.Enotes.dto.NotesResponse;
import com.example.Enotes.entity.FileDetails;
import com.example.Enotes.entity.Notes;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.repository.CategoryRepository;
import com.example.Enotes.repository.FileRepository;
import com.example.Enotes.repository.NotesRepository;
import com.example.Enotes.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper obj = new ObjectMapper();
        NotesDto notesDto = obj.readValue(notes,NotesDto.class);

        //update notes if id is given in request
        if(!ObjectUtils.isEmpty(notesDto.getId())){
            updateNotes(notesDto,file);
        }

        //category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto,Notes.class);
        notesMap.setIsDeleted(false);
        FileDetails fileDetails = saveFileDetails(file);

        if(!ObjectUtils.isEmpty(fileDetails)){
            notesMap.setFile(fileDetails);
        }else{
            if(ObjectUtils.isEmpty(notesDto.getId())){
                notesMap.setFile(null); //When id is also not present then make file as empty
            }
        }

        Notes saveNotes = notesRepository.save(notesMap);
        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
    }

    private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
        Notes existsNotes = notesRepository.findById(notesDto.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Invalid notes id"));

        if(ObjectUtils.isEmpty(file)){
            notesDto.setFileDetails(mapper.map(existsNotes.getFile(), NotesDto.FileDto.class));
            // If file is empty, set the previous file
        }

    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if(file!=null && !file.isEmpty()){

            String originalFileName = file.getOriginalFilename();
            String rndString = UUID.randomUUID().toString();
            String extension = FilenameUtils.getExtension(originalFileName); //dependency -> FilenameUtils
            String uploadFileName = rndString+"."+extension;

            File saveFile = new File(uploadPath);
            if(!saveFile.exists()){
                saveFile.mkdir();
            }

            // path : enotesapiservice/notes/java.pdf
            String strorePath = uploadPath.concat(uploadFileName);

            //upload file
            long upload = Files.copy(file.getInputStream(), Paths.get(strorePath));
            if(upload!=0){
                FileDetails fileDetails = new FileDetails();
                fileDetails.setOriginalFileName(originalFileName);
                fileDetails.setDisplayFileName(getDisplayName(originalFileName));
                fileDetails.setUploadFileName(uploadFileName);
                fileDetails.setFileSize(file.getSize());
                fileDetails.setPath(strorePath);
                FileDetails savedFile = fileRepository.save(fileDetails);
                return savedFile;
            }
        }
        return null;
    }

    private String getDisplayName(String originalFileName) {
        //filename.extension

        String extension = FilenameUtils.getExtension(originalFileName); //get extension
        String fileName = FilenameUtils.removeExtension(originalFileName); //get name without extension

        if(fileName.length()>8){
            fileName = fileName.substring(0,8);
        }

        fileName = fileName+"."+extension;
        return fileName;
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

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws Exception {

        try (InputStream io = new FileInputStream(fileDetails.getPath())) {
            return StreamUtils.copyToByteArray(io);
        }

    }

    @Override
    public FileDetails getFileDetails(Integer id) throws  Exception{
        FileDetails file = fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File is not available"));
        return file;
    }

    @Override
    public NotesResponse getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Notes> notes = notesRepository.findByCreatedByAndIsDeletedFalse(userId,pageable);

        List<NotesDto>  notesDtos = notes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

        NotesResponse notesResponse = NotesResponse.builder()
                .notes(notesDtos)
                .pageNo(notes.getNumber())
                .pageSize(notes.getSize())
                .totalElements(notes.getTotalElements())
                .totalPages(notes.getTotalPages())
                .isFirst(notes.isFirst())
                .isLast(notes.isLast())
                .build();

        return notesResponse;
    }

    @Override
    public void softDeleteNotes(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Notes id invalid or not found"));
        notes.setIsDeleted(true);
        notes.setDeletedOn(LocalDateTime.now());
        notesRepository.save(notes);
    }

    @Override
    public void restoreNotes(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Notes id invalid or not found"));
        notes.setIsDeleted(false);
        notes.setDeletedOn(null);
        notesRepository.save(notes);
    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(Integer useId) {
        List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(useId);
        List<NotesDto> notesDtosList = recycleNotes.stream().map((notes) -> mapper.map(notes,NotesDto.class)).toList();
        return notesDtosList;
    }

    @Override
    public void hardDeleteNotes(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Notes id invalid or not found"));
        if(notes.getIsDeleted()){
            notesRepository.delete(notes);
        }else {
            throw new IllegalArgumentException("Sorry You can't hard delete directly");
        }
    }

    @Override
    public void emptyRecycleBin(Integer userId) throws Exception {
        List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);

        if (!CollectionUtils.isEmpty(recycleNotes)){
            notesRepository.deleteAll(recycleNotes);
        }
    }


}
