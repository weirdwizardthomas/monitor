package com.example.monitor.service;

import com.example.monitor.model.monitored_endpoint.MonitoredEndpoint;
import com.example.monitor.model.monitoring_result.MonitoringResult;
import com.example.monitor.repository.MonitoredEndpointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class MonitoringBackgroundService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringBackgroundService.class);

    public static final int EVERY_SECOND = 1000;
    public static final String DEFAULT_PROTOCOL = "https://";
    private final MonitoredEndpointRepository monitoredEndpointRepository;
    private final MonitoringResultEntityService monitoringResultEntityService;

    private final TaskScheduler taskScheduler;

    private final ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    private final HttpClient httpClient = HttpClient.newHttpClient();


    @Autowired
    public MonitoringBackgroundService(MonitoredEndpointRepository monitoredEndpointRepository, MonitoringResultEntityService monitoringResultEntityService, TaskScheduler taskScheduler) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
        this.monitoringResultEntityService = monitoringResultEntityService;
        this.taskScheduler = taskScheduler;
    }

    @Scheduled(fixedDelay = EVERY_SECOND)
    public void checkAndRescheduleTasks() {
        List<MonitoredEndpoint> endpoints = monitoredEndpointRepository.findAll();
        for (MonitoredEndpoint endpoint : endpoints) {
            ScheduledFuture<?> existingTask = scheduledTasks.get(endpoint.getId());

            if (existingTask == null || existingTask.isCancelled() || existingTask.getDelay(TimeUnit.MILLISECONDS) <= 0) {
                long interval = endpoint.getMonitoredIntervalSeconds() * 1000L;
                scheduleTask(endpoint, interval);
            }
        }
    }

    private void scheduleTask(MonitoredEndpoint endpoint, long interval) {
        ScheduledFuture<?> newTask = taskScheduler.schedule(() -> pingEndpoint(endpoint),
                new java.util.Date(System.currentTimeMillis() + interval)
        );
        scheduledTasks.put(endpoint.getId(), newTask);
    }

    private URI constructUri(String url) {
        URI uri = URI.create(url);

        if (uri.getScheme() == null) {
            uri = URI.create(DEFAULT_PROTOCOL + url);
        }
        return uri;
    }

    private void pingEndpoint(MonitoredEndpoint endpoint) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(this.constructUri(endpoint.getUrl()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            MonitoringResult monitoringResult = new MonitoringResult();
            monitoringResult.setStatusCode(response.statusCode());
            monitoringResult.setPayload(response.body());
            monitoringResult = monitoringResultEntityService.create(monitoringResult, endpoint.getId());

            LOGGER.info("Response from %s: %d".formatted(endpoint.getUrl(), monitoringResult.getStatusCode()));
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Could not get response from %s: %s".formatted(endpoint.getUrl(), e));
        }
    }


}
