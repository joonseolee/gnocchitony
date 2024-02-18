package com.example.autobank.repository

import com.example.autobank.data.Comitee
import org.springframework.data.jpa.repository.JpaRepository

interface ComiteeRepository : JpaRepository<Comitee, Int>{
}