package com.coderhigher.auth.jobs;

import java.security.Principal;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/jobs")
public class JobsController {

    private final JobService jobService;

    public JobsController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/gmail-sync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, Object> startGmailSync(Principal principal) {
        JobStatusResponse job = jobService.startGmailSync(principal);
        return Map.of(
                "jobId", job.jobId(),
                "status", job.status(),
                "pollUrl", "/api/jobs/" + job.jobId()
        );
    }

    @GetMapping("/{jobId}")
    public JobStatusResponse getJob(@PathVariable String jobId) {
        JobStatusResponse job = jobService.getJob(jobId);
        if (job == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        return job;
    }
}
