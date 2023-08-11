package com.example.monitor.service;

import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import com.example.monitor.repository.MonitoredEndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoredEndpointEntityService {
    private final MonitoredEndpointRepository monitoredEndpointRepository;

    @Autowired
    public MonitoredEndpointEntityService(MonitoredEndpointRepository monitoredEndpointRepository) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
    }

    public List<MonitoredEndpoint> getAll() {
        return monitoredEndpointRepository.findAll();
    }

    public MonitoredEndpoint getById(Long id) {
        return monitoredEndpointRepository.getReferenceById(id);
    }

    public MonitoredEndpoint create(MonitoredEndpoint monitoredEndpoint) {
        return monitoredEndpointRepository.save(monitoredEndpoint);
    }

    public MonitoredEndpoint update(MonitoredEndpoint monitoredEndpoint) {
        return monitoredEndpointRepository.save(monitoredEndpoint);
    }

    public void deleteById(Long id) {
        monitoredEndpointRepository.deleteById(id);
    }
}
