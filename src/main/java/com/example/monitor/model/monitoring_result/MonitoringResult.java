package com.example.monitor.model.monitoring_result;

import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import jakarta.persistence.*;
import com.example.monitor.model.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class MonitoringResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date retrievedAt;
    private int statusCode;
    private String payload;
    @ManyToOne
    @JoinColumn(name = "monitored_endpoint_id")
    private MonitoredEndpoint monitoredEndpoint;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public MonitoredEndpoint getMonitoredEndpoint() {
        return monitoredEndpoint;
    }

    public void setMonitoredEndpoint(MonitoredEndpoint monitoredEndpointId) {
        this.monitoredEndpoint = monitoredEndpointId;
    }
}
