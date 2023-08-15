package com.example.monitor.service;

import com.example.monitor.configuration.AccessTokenFilter;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpointCreateDTO;
import com.example.monitor.model.user.User;
import com.example.monitor.repository.MonitoredEndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;

@Service
public class MonitoredEndpointEntityService {
    private final MonitoredEndpointRepository monitoredEndpointRepository;

    @Autowired
    public MonitoredEndpointEntityService(MonitoredEndpointRepository monitoredEndpointRepository) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
    }

    public List<MonitoredEndpoint> getAll() {
        User currentUser = AccessTokenFilter.getCurrentUser();

        return currentUser == null ? Collections.emptyList() : monitoredEndpointRepository.findByUser(currentUser);
    }

    public MonitoredEndpoint getById(Long id) throws AccessDeniedException {
        User currentUser = AccessTokenFilter.getCurrentUser();

        MonitoredEndpoint monitoredEndpoint = monitoredEndpointRepository.getReferenceById(id);

        if (!monitoredEndpoint.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You do not have permission to access this endpoint.");
        }
        return monitoredEndpoint;
    }

    public MonitoredEndpoint create(MonitoredEndpoint monitoredEndpoint) {
        monitoredEndpoint.setUser(AccessTokenFilter.getCurrentUser());
        return monitoredEndpointRepository.save(monitoredEndpoint);
    }

    public MonitoredEndpoint update(Long id, MonitoredEndpointCreateDTO monitoredEndpointDTO) throws AccessDeniedException {
        MonitoredEndpoint monitoredEndpoint = this.getById(id);

        monitoredEndpoint.setName(monitoredEndpointDTO.getName());
        monitoredEndpoint.setUrl(monitoredEndpoint.getUrl());
        monitoredEndpoint.setMonitoredIntervalSeconds(monitoredEndpoint.getMonitoredIntervalSeconds());

        return monitoredEndpointRepository.save(monitoredEndpoint);
    }

    public void deleteById(Long id) {
        monitoredEndpointRepository.deleteById(id);
    }
}
