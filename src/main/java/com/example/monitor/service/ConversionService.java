package com.example.monitor.service;

import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpointCreateDTO;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpointDTO;
import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.model.monitoring_result.MonitoringResultCreateDTO;
import com.example.monitor.model.monitoring_result.MonitoringResultDTO;
import com.example.monitor.model.user.User;
import com.example.monitor.model.user.UserCreateDTO;
import com.example.monitor.model.user.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversionService {
    private final ModelMapper modelMapper;

    @Autowired
    public ConversionService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MonitoredEndpointDTO convertToDTO(MonitoredEndpoint entity) {
        MonitoredEndpointDTO monitoredEndpointDTO = modelMapper.map(entity, MonitoredEndpointDTO.class);

        List<Long> monitoringResultDTOs = Optional.ofNullable(entity.getMonitoringResults())
                .map(results -> results.stream()
                        .map(MonitoringResult::getId)
                        .toList())
                .orElse(List.of());

        monitoredEndpointDTO.setMonitoringResultIds(monitoringResultDTOs);

        return monitoredEndpointDTO;
    }

    public MonitoredEndpoint convertToEntity(MonitoredEndpointDTO dto) {
        return modelMapper.map(dto, MonitoredEndpoint.class);
    }

    public MonitoredEndpoint convertToEntity(MonitoredEndpointCreateDTO dto) {
        return modelMapper.map(dto, MonitoredEndpoint.class);
    }

    public UserDTO convertToDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    public User convertToEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public User convertToEntity(UserCreateDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public MonitoringResultDTO convertToDTO(MonitoringResult entity) {
        MonitoringResultDTO monitoringResultDTO = modelMapper.map(entity, MonitoringResultDTO.class);
        monitoringResultDTO.setMonitoredEndpointId(entity.getMonitoredEndpoint().getId());
        return monitoringResultDTO;
    }

    public MonitoringResult convertToEntity(MonitoringResultDTO dto) {
        return modelMapper.map(dto, MonitoringResult.class);
    }

    public MonitoringResult convertToEntity(MonitoringResultCreateDTO dto) {
        return modelMapper.map(dto, MonitoringResult.class);
    }
}
