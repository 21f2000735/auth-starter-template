package com.coderhigher.auth.api;

import java.security.Principal;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/status")
    public Map<String, Object> status(Principal principal) {
        boolean authenticated = principal != null;
        return Map.of(
                "authenticated", authenticated,
                "username", authenticated ? principal.getName() : null,
                "loginUrl", "/oauth2/authorization/google",
                "logoutUrl", "/logout"
        );
    }
}
