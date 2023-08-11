package com.example.monitor.service;

import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.repository.MonitoringResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringResultEntityService {
    private final MonitoringResultRepository monitoringResultRepository;

    @Autowired
    public MonitoringResultEntityService(MonitoringResultRepository monitoringResultRepository) {
        this.monitoringResultRepository = monitoringResultRepository;
    }

    public MonitoringResult create(MonitoringResult monitoringResult) {
        return monitoringResultRepository.save(monitoringResult);
    }

    public List<MonitoringResult> getAll() {
        return monitoringResultRepository.findAll();
    }

    public MonitoringResult getById(Long id) {
        return monitoringResultRepository.getReferenceById(id);
    }

    public MonitoringResult update(MonitoringResult monitoringResult) {
        return monitoringResultRepository.save(monitoringResult);
    }

    public void deleteById(Long id) {
        monitoringResultRepository.deleteById(id);
    }
}
