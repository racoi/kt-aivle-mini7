package com.aivle.mini7.repository;

import com.aivle.mini7.model.EmergencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyInfoRepository extends JpaRepository<EmergencyInfo, String> {
}
