package com.example.monitor.model.monitored_endpoint;

import com.example.monitor.model.monitoring_result.MonitoringResultDTO;

import java.util.Date;
import java.util.List;

public class MonitoredEndpointDTO {
    private Long id;
    private String name;
    private String url;
    private Date createdAt;
    private Date lastCheckedAt;
    private int monitoredIntervalSeconds;
    private List<Long> monitoringResultIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastCheckedAt() {
        return lastCheckedAt;
    }

    public void setLastCheckedAt(Date lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public int getMonitoredIntervalSeconds() {
        return monitoredIntervalSeconds;
    }

    public void setMonitoredIntervalSeconds(int monitoredIntervalSeconds) {
        this.monitoredIntervalSeconds = monitoredIntervalSeconds;
    }

    public List<Long> getMonitoringResultIds() {
        return monitoringResultIds;
    }

    public void setMonitoringResultIds(List<Long> monitoringResultIds) {
        this.monitoringResultIds = monitoringResultIds;
    }
}

