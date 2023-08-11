package com.example.monitor.model.monitored_endpoint;

public class MonitoredEndpointCreateDTO {

    private String name;
    private String url;
    private int monitoredIntervalSeconds;

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

    public int getMonitoredIntervalSeconds() {
        return monitoredIntervalSeconds;
    }

    public void setMonitoredIntervalSeconds(int monitoredIntervalSeconds) {
        this.monitoredIntervalSeconds = monitoredIntervalSeconds;
    }
}
