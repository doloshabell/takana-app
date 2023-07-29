package com.example.takana.service

import android.content.Context
import com.example.takana.BuildConfig
import com.example.takana.data.util.UserToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys

class JwtDecode {


    fun decodeJwt(context: Context, jwtToken: String) {
        val secretKey = Keys.hmacShaKeyFor(BuildConfig.SECRET_KEY.toByteArray())
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(jwtToken)
            .body

        val name = claims.get("name", String::class.java)
        val userName = claims.get("username", String::class.java)
        val userId = (claims.get("userId", Integer::class.java))?.toLong()

        UserToken.saveObjectToSharedPreferences(context, name, userName, userId)
    }
}