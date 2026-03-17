package com.coderhigher.auth.jobs;

public record JobStatusResponse(
        String jobId,
        String jobType,
        String status,
        int progress,
        String startedBy,
        String startedAt,
        String finishedAt,
        String message
) {
}
