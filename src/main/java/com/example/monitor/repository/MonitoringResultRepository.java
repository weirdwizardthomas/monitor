package com.example.monitor.repository;

import com.example.monitor.model.monitoring_result.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {
}
