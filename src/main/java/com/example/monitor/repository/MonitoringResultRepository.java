package com.example.monitor.repository;

import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {
    List<MonitoringResult> findByUser(User user);
}
