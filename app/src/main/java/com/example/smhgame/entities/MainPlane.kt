package com.example.smhgame.entities

import android.graphics.drawable.Drawable

class MainPlane(
    override var x: Float,
    override var y: Float,
    override val texture: Drawable,
    override val onShot: () -> Unit
) : Tech, Shootable {
    override var currentHp = 12

    override val maxHP: Int = 12
}