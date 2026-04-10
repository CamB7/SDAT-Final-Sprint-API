package com.keyin.rest.authentication;

import java.util.List;

public record CurrentUserResponse(
        String username,
        List<String> roles
) {
}

