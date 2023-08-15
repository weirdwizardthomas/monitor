package com.example.monitor.service;

import com.example.monitor.configuration.AccessTokenFilter;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.model.user.User;
import com.example.monitor.repository.MonitoredEndpointRepository;
import com.example.monitor.repository.MonitoringResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
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
        User currentUser = AccessTokenFilter.getCurrentUser();

        return currentUser == null ? Collections.emptyList() : monitoringResultRepository.findByUser(currentUser);
    }

    public MonitoringResult getById(Long id) throws AccessDeniedException {
        User currentUser = AccessTokenFilter.getCurrentUser();

        MonitoringResult monitoringResult = monitoringResultRepository.getReferenceById(id);

        if (!monitoringResult.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You do not have permission to access this result.");
        }
        return monitoringResult;
    }

    public void deleteById(Long id) {
        monitoringResultRepository.deleteById(id);
    }
}
