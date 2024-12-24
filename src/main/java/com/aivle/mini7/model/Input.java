package com.aivle.mini7.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "input")
public class Input {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_id")
    private Long inputId;

    @Column(name = "timestamp", nullable = false)
    private String datetime;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "em_count", nullable = false)
    private double emCount;

}
