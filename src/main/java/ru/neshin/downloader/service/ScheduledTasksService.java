package ru.neshin.downloader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasksService {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasksService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final ReadFilesClient readFilesClient;

    @Autowired
    public ScheduledTasksService(ReadFilesClient readFilesClient) {
        this.readFilesClient = readFilesClient;
    }

    @Scheduled(fixedRateString = "${downloader.scheduled.time.milliseconds}")
    public void reportCurrentTime() {
        LOG.info("The time is now {}", dateFormat.format(new Date()));
        readFilesClient.work();
    }

}