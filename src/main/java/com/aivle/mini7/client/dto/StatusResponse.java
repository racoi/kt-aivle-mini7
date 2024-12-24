package com.aivle.mini7.client.dto;

import ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StatusResponse {
    private String status;
    private String message;
    private List<HospitalResponse> data;
    private int grade;
}
