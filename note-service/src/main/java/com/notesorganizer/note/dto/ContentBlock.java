package com.notesorganizer.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentBlock {
    private String type;     // paragraph, bullet, numbered, etc.
    private String content;
}