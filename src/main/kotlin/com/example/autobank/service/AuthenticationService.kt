package com.example.autobank.service

import com.example.autobank.data.authentication.Auth0User
import org.springframework.stereotype.Service
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.exchange


@Service
class AuthenticationService {

    private val restTemplate = RestTemplate()

    @Value("\${auth0.domain}")
    private val domain: String = ""

    fun getAuth0User(token: String): Auth0User {
        return Auth0User("sub", "email", "name")
    }

    fun getUserSub(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication is JwtAuthenticationToken) {
            val token = authentication.token
            token.getClaim("sub")
        } else {
            ""
        }
    }

    fun getAccessToken(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication is JwtAuthenticationToken) {
            val token = authentication.token
            token.tokenValue
        } else {
            ""
        }
    }

    fun getUserDetails(): Auth0User {
        val headers = HttpHeaders().apply {
            set("Authorization", "Bearer ${getAccessToken()}")
        }
        val entity = HttpEntity<Void>(headers)
        val response: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            "${domain}/userinfo",
            HttpMethod.GET,
            entity,
        )

        return Auth0User(
            sub = response.body?.get("sub").toString(),
            email = response.body?.get("email").toString(),
            name = response.body?.get("name").toString(),
        )
    }

}