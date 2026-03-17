package com.coderhigher.auth.api.dto;

import java.util.List;

public record DashboardSummaryResponse(
        String appName,
        String environment,
        boolean authenticated,
        String username,
        List<SummaryCard> cards,
        List<RecentAction> recentActions
) {
    public record SummaryCard(String title, String value, String hint) {}
    public record RecentAction(String action, String status, String note) {}
}
