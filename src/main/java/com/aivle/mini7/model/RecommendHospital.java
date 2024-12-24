package com.aivle.mini7.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recommend_hospital")
public class RecommendHospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="institution_code")
    private  EmergencyInfo institutionCode;

    @ManyToOne
    @JoinColumn(name = "input_id") // 관계 매핑
    private Input inputId;

    @Column(name = "distance", nullable = false)
    private String distance;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "departure_time", nullable = false)
    private String departureTime;

    @Column(name = "arrival_time", nullable = false)
    private String arrivalTime;


}
