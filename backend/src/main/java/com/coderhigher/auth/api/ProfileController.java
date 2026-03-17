package com.coderhigher.auth.api;

import java.security.Principal;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @GetMapping("/me")
    public Map<String, Object> me(Principal principal, @AuthenticationPrincipal OAuth2User oauth2User) {
        String username = principal == null ? "anonymous" : principal.getName();
        String name = oauth2User == null ? null : oauth2User.getAttribute("name");
        String email = oauth2User == null ? null : oauth2User.getAttribute("email");
        String picture = oauth2User == null ? null : oauth2User.getAttribute("picture");

        return Map.of(
                "username", username,
                "name", name,
                "email", email,
                "picture", picture,
                "authenticated", principal != null
        );
    }
}
