package com.example.monitor.controller;


import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpointCreateDTO;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpointDTO;
import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.model.monitoring_result.MonitoringResultDTO;
import com.example.monitor.service.ConversionService;
import com.example.monitor.service.MonitoredEndpointEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/monitored_endpoint")
public class MonitoredEndpointController {

    public static final String DEFAULT_LIMIT = "10";
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
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody MonitoredEndpointDTO createMonitoredEndpoint(@RequestBody MonitoredEndpointCreateDTO monitoredEndpointDTO) {
        MonitoredEndpoint monitoredEndpoint = conversionService.convertToEntity(monitoredEndpointDTO);
        monitoredEndpoint = monitoredEndpointEntityService.create(monitoredEndpoint);
        return conversionService.convertToDTO(monitoredEndpoint);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody MonitoredEndpointDTO getMonitoredEndpointById(@PathVariable Long id) throws AccessDeniedException {
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointEntityService.getById(id);
        return conversionService.convertToDTO(monitoredEndpoint);
    }

    @GetMapping(path = "/{id}/list_results")
    public @ResponseBody List<MonitoringResultDTO> getLatestForId(@PathVariable Long id, @RequestParam(name = "limit", defaultValue = DEFAULT_LIMIT) int limit) throws AccessDeniedException {
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointEntityService.getById(id);

        return Optional.ofNullable(monitoredEndpoint.getMonitoringResults()).orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(MonitoringResult::getRetrievedAt).reversed())
                .limit(limit)
                .map(conversionService::convertToDTO)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody MonitoredEndpointDTO updateMonitoredEndpoint(@PathVariable Long id, @RequestBody MonitoredEndpointCreateDTO monitoredEndpointDTO) throws AccessDeniedException {
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointEntityService.update(id, monitoredEndpointDTO);
        return conversionService.convertToDTO(monitoredEndpoint);
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody void deleteMonitoredEndpointById(@PathVariable Long id) {
        monitoredEndpointEntityService.deleteById(id);
    }
}
