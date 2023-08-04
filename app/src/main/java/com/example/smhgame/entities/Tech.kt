package com.example.smhgame.entities

import android.graphics.drawable.Drawable

interface Tech {
    val texture: Drawable
    val maxHP: Int
    var currentHp: Int
    var x: Float
    var y: Float
}