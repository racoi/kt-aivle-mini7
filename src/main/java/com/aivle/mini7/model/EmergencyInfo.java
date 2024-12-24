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
@Table(name = "ED_ER_info")
public class EmergencyInfo {
    @Id
    @Column(name="institution_code")
    private String institutionCode;
    @Column(nullable = false)
    private String emergency_operating_status;
    @Column(nullable = false)
    private String institution_name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String main_phone;
    @Column(nullable = false)
    private String emergency_phone;
    @Column(nullable = false)
    private int emergency_bed_count;
    @Column(nullable = false)
    private int inpatient_bed_count;
    @Column(nullable = false)
    private String ct_availability;
    @Column(nullable = false)
    private String mri_availability;
    @Column(nullable = false)
    private String ventilator_availability;
    @Column(nullable = false)
    private String ambulance_availability;
    @Column(nullable = false)
    private int operating_room_bed_count;
    @Column(nullable = false)
    private int inpatient_availability;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;
}
