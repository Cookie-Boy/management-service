package ru.platform.management.api.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

@Scheduled(cron = "0 30 2 * * ?")
public class MedicationScheduler {
}
