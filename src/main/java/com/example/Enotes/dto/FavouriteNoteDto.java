package com.example.Enotes.dto;

import com.example.Enotes.entity.Notes;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteNoteDto {
    private Integer id;
    private NotesDto note;
    private Integer userId;
}
