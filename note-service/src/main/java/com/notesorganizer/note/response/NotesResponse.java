package com.notesorganizer.note.response;

import com.notesorganizer.note.dto.ContentBlock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotesResponse {

    private String id;
    private String userId;
    private String title;
    private List<ContentBlock> contentBlocks;
    private Boolean isPinned;
    private Boolean isSummarized;

}
