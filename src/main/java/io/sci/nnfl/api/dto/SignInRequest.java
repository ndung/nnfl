package io.sci.nnfl.api.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(@NotBlank String username, @NotBlank String password) {}

