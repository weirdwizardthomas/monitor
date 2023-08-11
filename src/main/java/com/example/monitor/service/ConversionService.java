package com.example.monitor.service;

import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpointCreateDTO;
import com.example.monitor.model.monitored_endpoint.MonitoredEndpointDTO;
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
}
