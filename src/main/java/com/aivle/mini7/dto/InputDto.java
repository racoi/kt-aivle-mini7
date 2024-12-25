package com.aivle.mini7.dto;

import jakarta.persistence.Column;
import lombok.*;

public class InputDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Get{
        private String detail;
        private double latitude;
        private double longitude;
        private double emCount;
    }
}
