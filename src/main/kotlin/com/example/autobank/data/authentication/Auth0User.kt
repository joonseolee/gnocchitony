package com.example.autobank.data.authentication

import org.jetbrains.annotations.NotNull


/*
    Class with data from auth received from frontend
 */
class Auth0User (
    @NotNull
    val sub: String,
    @NotNull
    val email: String,
    @NotNull
    val name: String,
)