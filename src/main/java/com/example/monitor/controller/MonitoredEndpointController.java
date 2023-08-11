package com.example.monitor.controller;


import com.example.monitor.model.monitored_endpoint.MonitoredEndpointCreateDTO;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpointDTO;
import com.example.monitor.service.MonitoredEndpointEntityService;
import com.example.monitor.service.ConversionService;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/monitored_endpoint")
public class MonitoredEndpointController {

    private final ConversionService conversionService;
    private final MonitoredEndpointEntityService monitoredEndpointEntityService;

    @Autowired
    public MonitoredEndpointController(ConversionService conversionService, MonitoredEndpointEntityService monitoredEndpointEntityService) {
        this.conversionService = conversionService;
        this.monitoredEndpointEntityService = monitoredEndpointEntityService;
    }

    @GetMapping
    public @ResponseBody List<MonitoredEndpointDTO> getAllMonitoredEndpoints() {
        List<MonitoredEndpoint> monitoredEndpoints = monitoredEndpointEntityService.getAll();
        return monitoredEndpoints
                .stream()
                .map(conversionService::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public @ResponseBody MonitoredEndpointDTO createMonitoredEndpoint(@RequestBody MonitoredEndpointCreateDTO monitoredEndpointDTO) {
        MonitoredEndpoint monitoredEndpoint = conversionService.convertToEntity(monitoredEndpointDTO);
        monitoredEndpoint = monitoredEndpointEntityService.create(monitoredEndpoint);
        return conversionService.convertToDTO(monitoredEndpoint);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody MonitoredEndpointDTO getMonitoredEndpointById(@PathVariable Long id) {
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointEntityService.getById(id);
        return conversionService.convertToDTO(monitoredEndpoint);
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody MonitoredEndpointDTO updateMonitoredEndpoint(@PathVariable Long id, @RequestBody MonitoredEndpointCreateDTO monitoredEndpointDTO) {
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointEntityService.getById(id);

        monitoredEndpoint.setName(monitoredEndpointDTO.getName());
        monitoredEndpoint.setUrl(monitoredEndpoint.getUrl());
        monitoredEndpoint.setMonitoredIntervalSeconds(monitoredEndpoint.getMonitoredIntervalSeconds());

        monitoredEndpoint = monitoredEndpointEntityService.update(monitoredEndpoint);
        return conversionService.convertToDTO(monitoredEndpoint);
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody void deleteMonitoredEndpointById(@PathVariable Long id) {
        monitoredEndpointEntityService.deleteById(id);
    }
}
