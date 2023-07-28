package com.example.takana.service

import com.example.takana.BuildConfig
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys

class JwtDecode {

    fun decodeJwt(jwtToken: String): Map<String, Any> {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(BuildConfig.SECRET_KEY.toByteArray()))
            .build()
            .parseClaimsJws(jwtToken)
            .body
    }
}