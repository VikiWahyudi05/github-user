package com.sib.githubuser.model

data class DetailUserResponse(
    val login: String,
    val avatar_url: String,
    val name: String,
    val company: String,
    val location: String,
    val public_repos: String,
    val following: Int,
    val followers: Int
)
