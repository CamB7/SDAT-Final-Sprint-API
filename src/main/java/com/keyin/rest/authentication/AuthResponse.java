package com.keyin.rest.authentication;

import java.util.List;

public record AuthResponse(
        String username,
        List<String> roles
) {
}

