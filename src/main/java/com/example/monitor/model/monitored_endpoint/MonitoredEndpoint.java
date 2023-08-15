package com.example.monitor.model.monitored_endpoint;

import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
public class MonitoredEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String url;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastCheckedAt;
    private int monitoredIntervalSeconds;

    @OneToMany(mappedBy = "monitoredEndpoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonitoringResult> monitoringResults;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public void setLastCheckedAt(Date lastCheckAt) {
        this.lastCheckedAt = lastCheckAt;
    }

    public int getMonitoredIntervalSeconds() {
        return monitoredIntervalSeconds;
    }

    public void setMonitoredIntervalSeconds(int monitoredIntervalSeconds) {
        this.monitoredIntervalSeconds = monitoredIntervalSeconds;
    }

    public List<MonitoringResult> getMonitoringResults() {
        return monitoringResults;
    }

    public void setMonitoringResults(List<MonitoringResult> monitoringResults) {
        this.monitoringResults = monitoringResults;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
