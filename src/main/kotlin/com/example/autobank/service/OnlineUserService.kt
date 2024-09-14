package com.example.autobank.service

import com.example.autobank.data.authentication.Auth0User
import com.example.autobank.data.authentication.AuthenticatedUserResponse
import com.example.autobank.data.user.OnlineUser
import com.example.autobank.repository.user.OnlineUserRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired


@Service
class OnlineUserService(val repository: OnlineUserRepository, @Autowired val onlineUserRepository: OnlineUserRepository) {

    @Autowired
    lateinit var authenticationService: AuthenticationService

    fun checkStoredUserBySub(sub: String): AuthenticatedUserResponse {
        if (sub.isEmpty()) {
            return AuthenticatedUserResponse(success = false, false)
        }
        val storedUser = onlineUserRepository.findByOnlineId(sub)
        return if (storedUser != null) {
            AuthenticatedUserResponse(success = true, false)
        } else {
            return createOnlineUser()
        }
    }

    fun createOnlineUser(): AuthenticatedUserResponse {
        try {
            val userinfo: Auth0User = authenticationService.getUserDetails()
            val onlineUser = OnlineUser(
                id = "",
                onlineId = userinfo.sub,
                email = userinfo.email,
                fullname = userinfo.name,
            )

            if (onlineUser.onlineId.isEmpty() || onlineUser.email.isEmpty() || onlineUser.fullname.isEmpty()) {
                return AuthenticatedUserResponse(success = false, false)
            }

            onlineUserRepository.save(onlineUser)

            return AuthenticatedUserResponse(success = true, false)
        } catch (e: Exception) {
            println(e)
            return AuthenticatedUserResponse(success = false, false)
        }
    }
}
