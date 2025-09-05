package com.example.Enotes.repository;

import com.example.Enotes.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails,Integer> {
}
