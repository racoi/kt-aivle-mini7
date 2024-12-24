package com.aivle.mini7.service;

import com.aivle.mini7.model.EmergencyInfo;
import com.aivle.mini7.repository.EmergencyInfoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class CsvDataInitializer implements CommandLineRunner {

    private final EmergencyInfoRepository emergencyInfoRepository;

    public CsvDataInitializer(EmergencyInfoRepository emergencyInfoRepository){
        this.emergencyInfoRepository = emergencyInfoRepository;
    }

    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    @Override
    public void run(String... args) throws IOException {
        ClassPathResource resource = new ClassPathResource("ED_ER_info.csv");
        InputStream inputStream = resource.getInputStream();
        BOMInputStream bomInputStream = new BOMInputStream(inputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreEmptyLines()
                .parse(reader);

        for (CSVRecord record : records) {
            String institutionCode = record.get("institution_code");
            if (emergencyInfoRepository.existsById(institutionCode)) {
                continue;
            }
            EmergencyInfo emergencyInfo = new EmergencyInfo();
            emergencyInfo.setInstitutionCode(record.get("institution_code"));
            emergencyInfo.setCt_availability(record.get("ct_availability"));
            emergencyInfo.setMri_availability(record.get("mri_availability"));
            emergencyInfo.setVentilator_availability(record.get("ventilator_availability"));
            emergencyInfo.setAmbulance_availability(record.get("ambulance_availability"));
            emergencyInfo.setInstitution_name(record.get("institution_name"));
            emergencyInfo.setAddress(record.get("address"));
            emergencyInfo.setMain_phone(record.get("main_phone"));
            emergencyInfo.setEmergency_phone(record.get("emergency_phone"));
            emergencyInfo.setEmergency_bed_count(parseInteger(record.get("emergency_bed_count")));
            emergencyInfo.setEmergency_operating_status(record.get("emergency_operating_status"));
            emergencyInfo.setOperating_room_bed_count(parseInteger(record.get("operating_room_bed_count")));
            emergencyInfo.setInpatient_bed_count(parseInteger(record.get("inpatient_bed_count")));
            emergencyInfo.setInpatient_availability(parseInteger(record.get("inpatient_availability")));
            emergencyInfo.setLatitude(Double.parseDouble(record.get("latitude")));
            emergencyInfo.setLongitude(Double.parseDouble(record.get("longitude")));

            emergencyInfoRepository.save(emergencyInfo);
        }
    }
}