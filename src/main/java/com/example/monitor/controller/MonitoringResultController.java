package com.example.monitor.controller;

import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.model.monitoring_result.MonitoringResultDTO;
import com.example.monitor.service.ConversionService;
import com.example.monitor.service.MonitoringResultEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequestMapping(path = "/monitoring_result")
public class MonitoringResultController {
    private final ConversionService conversionService;
    private final MonitoringResultEntityService monitoringResultEntityService;

    @Autowired
    public MonitoringResultController(ConversionService conversionService, MonitoringResultEntityService monitoringResultEntityService) {
        this.conversionService = conversionService;
        this.monitoringResultEntityService = monitoringResultEntityService;
    }

    @GetMapping
    public @ResponseBody List<MonitoringResultDTO> getMonitoringResults() {
        return monitoringResultEntityService.getAll()
                .stream()
                .map(conversionService::convertToDTO)
                .toList();
    }

    @GetMapping(path = "/{monitoringResultId}")
    public @ResponseBody MonitoringResultDTO getMonitoringResultById(@PathVariable Long monitoringResultId) throws AccessDeniedException {
        MonitoringResult monitoringResult = monitoringResultEntityService.getById(monitoringResultId);
        return conversionService.convertToDTO(monitoringResult);
    }

    @DeleteMapping(path = "/{monitoringResultId}")
    public @ResponseBody void deleteUserById(@PathVariable Long monitoringResultId) {
        monitoringResultEntityService.deleteById(monitoringResultId);
    }
}
