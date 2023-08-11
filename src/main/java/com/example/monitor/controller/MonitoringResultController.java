package com.example.monitor.controller;

import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.model.monitoring_result.MonitoringResultCreateDTO;
import com.example.monitor.model.monitoring_result.MonitoringResultDTO;
import com.example.monitor.service.ConversionService;
import com.example.monitor.service.MonitoringResultEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "monitoring_result")
public class MonitoringResultController {
    private final ConversionService conversionService;
    private final MonitoringResultEntityService monitoringResultEntityService;

    @Autowired
    public MonitoringResultController(ConversionService conversionService, MonitoringResultEntityService monitoringResultEntityService) {
        this.conversionService = conversionService;
        this.monitoringResultEntityService = monitoringResultEntityService;
    }

    @GetMapping
    public @ResponseBody List<MonitoringResultDTO> getAllMonitoringResults() {
        return monitoringResultEntityService.getAll()
                .stream()
                .map(conversionService::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public @ResponseBody MonitoringResultDTO createUser(@RequestBody MonitoringResultCreateDTO monitoringResultCreateDTO) {
        MonitoringResult monitoringResult = conversionService.convertToEntity(monitoringResultCreateDTO);
        monitoringResult = monitoringResultEntityService.create(monitoringResult);
        return conversionService.convertToDTO(monitoringResult);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody MonitoringResultDTO getUserById(@PathVariable Long id) {
        MonitoringResult monitoringResult = monitoringResultEntityService.getById(id);
        return conversionService.convertToDTO(monitoringResult);
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody MonitoringResultDTO updateUser(@PathVariable Long id, @RequestBody MonitoringResultCreateDTO monitoringResultDTO) {
        MonitoringResult monitoringResult = monitoringResultEntityService.getById(id);

        monitoringResult.setPayload(monitoringResult.getPayload());
        monitoringResult.setStatusCode(monitoringResultDTO.getStatusCode());
        monitoringResult.setRetrievedAt(monitoringResultDTO.getRetrievedAt());

        monitoringResult = monitoringResultEntityService.update(monitoringResult);
        return conversionService.convertToDTO(monitoringResult);
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody void deleteUserById(@PathVariable Long id) {
        monitoringResultEntityService.deleteById(id);
    }
}
