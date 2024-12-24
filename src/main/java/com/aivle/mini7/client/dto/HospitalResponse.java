package com.aivle.mini7.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HospitalResponse {
    private String institution_code;
    private String emergency_operating_status;
    private String institution_name;
    private String address;
    private String main_phone;
    private String emergency_phone;
    private int emergency_bed_count;
    private int inpatient_bed_count;
    private String ct_availability;
    private String mri_availability;
    private String ventilator_availability;
    private String ambulance_availability;
    private String distance;
    private String duration;
    private String departureTime;
    private String arrivalTime;
    private int operating_room_bed_count;
    private int inpatient_availability;
    private double latitude;
    private double longitude;

}



