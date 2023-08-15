package com.example.monitor.model.monitoring_result;

import com.example.monitor.model.monitored_endpoint.MonitoredEndpointDTO;

import java.util.Date;

public class MonitoringResultDTO {
    private Long id;
    private int statusCode;
    private String payload;

    private Date retrievedAt;

    private long monitoredEndpointId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRetrievedAt() {
        return retrievedAt;
    }

    public void setRetrievedAt(Date retrievedAt) {
        this.retrievedAt = retrievedAt;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public long getMonitoredEndpointId() {
        return monitoredEndpointId;
    }

    public void setMonitoredEndpointId(long monitoredEndpointId) {
        this.monitoredEndpointId = monitoredEndpointId;
    }
}
