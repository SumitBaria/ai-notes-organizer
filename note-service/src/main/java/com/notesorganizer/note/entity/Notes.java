package com.notesorganizer.note.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notesorganizer.note.dto.ContentBlock;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
public class Notes extends Auditable {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Setter
    @Getter
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID userId;
    @Setter
    @Getter
    private String title;

    @Column(columnDefinition = "json", name = "content_blocks")
    private String contentBlocksJson;
    @Setter
    @Getter
    private boolean isPinned;
    @Setter
    @Getter
    private boolean isSummarized;

    @Transient
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Transient
    public List<ContentBlock> getContentBlocks() {
        try {
            return objectMapper.readValue(contentBlocksJson, new TypeReference<List<ContentBlock>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse contentBlocksJson", e);
        }
    }

    @Transient
    public void setContentBlocks(List<ContentBlock> blocks) {
        try {
            this.contentBlocksJson = objectMapper.writeValueAsString(blocks);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize contentBlocks", e);
        }
    }


}
