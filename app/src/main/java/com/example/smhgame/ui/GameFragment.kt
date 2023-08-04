package com.example.smhgame.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.smhgame.R
import com.example.smhgame.databinding.FragmentMainPlaneBinding
import com.example.smhgame.entities.MainPlane
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val BULLET_SPEED = 10L
const val BULLET_TIME = 5L
const val BULLET_SIZE = 7
const val RELOAD_TIME = 1000L

class MainPlaneFragment : Fragment() {
    private var _binding: FragmentMainPlaneBinding? = null
    private val binding get() = _binding!!

    private val bulletSize get() = BULLET_SIZE * binding.plane.width / 50
    private val bulletX get() = binding.plane.x + binding.plane.width / 2

    private lateinit var plane: MainPlane

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPlaneBinding.inflate(inflater, container, false)
        startGame()

        // Plane Movement
        val onTouchListener = View.OnTouchListener { view, event ->
            val newX = event.rawX - view.width

            view.animate()
                .x(newX)
                .setDuration(0)
                .start()

            plane.x = newX

            view.performClick()
            true
        }

        binding.plane.setOnTouchListener(onTouchListener)

        MainScope().launch {
            while (true) {
                shoot()
                delay(RELOAD_TIME)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startGame() {
        plane = MainPlane(
            binding.root.width / 2f,
            binding.root.y,
            ResourcesCompat.getDrawable(resources, R.drawable.main_plane, null)!!
        ) {
            shoot()
        }
    }

    private fun shoot() {
        val bullet = ImageView(requireContext()).apply {
            setImageResource(R.drawable.bullet)
            x = bulletX //plane.x + binding.plane.width / 2
            y = binding.plane.y
        }

        binding.root.addView(bullet)
        bullet.layoutParams.width = bulletSize

        MainScope().launch {
            while (true) {
                bullet.y -= BULLET_SPEED
                delay(BULLET_TIME)

                if (bullet.y <= binding.root.y - 500) {
                    binding.root.removeView(bullet)
                    break
                }
            }
        }
    }
}