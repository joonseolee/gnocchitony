package com.example.autobank.data.authentication

import org.jetbrains.annotations.NotNull


class Auth0User(
    @NotNull
    val sub: String,
    @NotNull
    val email: String,
    @NotNull
    val name: String,
)