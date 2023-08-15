package com.example.monitor.service;

import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.repository.MonitoredEndpointRepository;
import com.example.monitor.repository.MonitoringResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringResultEntityService {
    private final MonitoringResultRepository monitoringResultRepository;
    private final MonitoredEndpointRepository monitoredEndpointRepository;

    @Autowired
    public MonitoringResultEntityService(MonitoringResultRepository monitoringResultRepository, MonitoredEndpointRepository monitoredEndpointRepository) {
        this.monitoringResultRepository = monitoringResultRepository;
        this.monitoredEndpointRepository = monitoredEndpointRepository;
    }

    public MonitoringResult create(MonitoringResult monitoringResult, Long parentId) {
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointRepository.getReferenceById(parentId);
        monitoringResult.setMonitoredEndpoint(monitoredEndpoint);
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
