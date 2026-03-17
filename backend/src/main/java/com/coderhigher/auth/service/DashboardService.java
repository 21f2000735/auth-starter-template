package com.coderhigher.auth.service;

import com.coderhigher.auth.api.dto.DashboardSummaryResponse;
import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    public DashboardSummaryResponse summary(Principal principal) {
        boolean authenticated = principal != null;
        String username = authenticated ? principal.getName() : "guest";

        return new DashboardSummaryResponse(
                "OpenClaw Gmail",
                "localhost",
                authenticated,
                username,
                List.of(
                        new DashboardSummaryResponse.SummaryCard("Auth mode", "Google OAuth", "Backend-heavy session flow"),
                        new DashboardSummaryResponse.SummaryCard("Frontend", "Vue 3 + Bootstrap", "Starter dashboard shell ready"),
                        new DashboardSummaryResponse.SummaryCard("Backend", "Spring Boot", "Protected APIs and profile endpoint wired")
                ),
                List.of(
                        new DashboardSummaryResponse.RecentAction("Google login", authenticated ? "WORKING" : "READY", authenticated ? "Authenticated session confirmed" : "Awaiting active user session"),
                        new DashboardSummaryResponse.RecentAction("Profile fetch", authenticated ? "WORKING" : "READY", "Driven by /api/me"),
                        new DashboardSummaryResponse.RecentAction("Keycloak phase", "LATER", "Kept as future extension path")
                )
        );
    }
}
