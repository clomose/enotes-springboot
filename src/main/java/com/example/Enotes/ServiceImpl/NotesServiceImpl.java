package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.NotesDto;
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
import org.springframework.stereotype.Service;
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

        //category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto,Notes.class);

        FileDetails fileDetails = saveFileDetails(file);

        if(!ObjectUtils.isEmpty(fileDetails)){
            notesMap.setFile(fileDetails);
        }else{
            notesMap.setFile(null);
        }

        Notes saveNotes = notesRepository.save(notesMap);
        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
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
}
