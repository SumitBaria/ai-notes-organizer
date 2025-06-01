package com.notesorganizer.common.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiResponse<T> {

    @Builder.Default
    private boolean success = true;
    private String message;
    private T data;

}
