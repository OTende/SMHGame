package com.example.smhgame.entities

interface Shootable {
//    fun shoot(reloadTime: Long)
    val onShot: () -> Unit
}