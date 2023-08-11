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

@Service
public class ConversionService {
    private final ModelMapper modelMapper;

    @Autowired
    public ConversionService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MonitoredEndpointDTO convertToDTO(MonitoredEndpoint entity) {
        return modelMapper.map(entity, MonitoredEndpointDTO.class);
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
        return modelMapper.map(entity, MonitoringResultDTO.class);
    }

    public MonitoringResult convertToEntity(MonitoringResultDTO dto) {
        return modelMapper.map(dto, MonitoringResult.class);
    }

    public MonitoringResult convertToEntity(MonitoringResultCreateDTO dto) {
        return modelMapper.map(dto, MonitoringResult.class);
    }
}
