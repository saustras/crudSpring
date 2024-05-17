package com.handle.handle.exception;


import lombok.Builder;

@Builder
public record ErrorResponse(Integer code, String message) {
}
