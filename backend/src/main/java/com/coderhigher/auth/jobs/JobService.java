package com.coderhigher.auth.jobs;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final Map<String, JobStatusResponse> jobs = new ConcurrentHashMap<>();

    public JobStatusResponse startGmailSync(Principal principal) {
        String jobId = UUID.randomUUID().toString();
        String username = principal == null ? "unknown" : principal.getName();
        JobStatusResponse queued = new JobStatusResponse(
                jobId,
                "gmail-sync",
                "QUEUED",
                0,
                username,
                OffsetDateTime.now().toString(),
                null,
                "Job accepted"
        );
        jobs.put(jobId, queued);
        runGmailSync(jobId, username);
        return queued;
    }

    public JobStatusResponse getJob(String jobId) {
        return jobs.get(jobId);
    }

    @Async
    public CompletableFuture<Void> runGmailSync(String jobId, String username) {
        update(jobId, "RUNNING", 15, username, null, "Connecting to Gmail sample flow");
        sleep(1200);
        update(jobId, "RUNNING", 55, username, null, "Reading inbox metadata");
        sleep(1200);
        update(jobId, "RUNNING", 85, username, null, "Preparing dashboard summary");
        sleep(900);
        update(jobId, "SUCCEEDED", 100, username, OffsetDateTime.now().toString(), "Demo Gmail sync completed successfully");
        return CompletableFuture.completedFuture(null);
    }

    private void update(String jobId, String status, int progress, String username, String finishedAt, String message) {
        JobStatusResponse current = jobs.get(jobId);
        if (current == null) {
            return;
        }
        jobs.put(jobId, new JobStatusResponse(
                current.jobId(),
                current.jobType(),
                status,
                progress,
                username,
                current.startedAt(),
                finishedAt,
                message
        ));
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
