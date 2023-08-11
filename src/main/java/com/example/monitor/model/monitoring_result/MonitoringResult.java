package com.example.monitor.model.monitoring_result;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class MonitoringResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date retrievedAt;
    private int statusCode;

    private String payload;
/*
    @ManyToOne
    @JoinColumn(name = "monitored_endpoint_id")
    private MonitoredEndpoint monitoredEndpointId;*/

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

}
