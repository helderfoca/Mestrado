package com.example.animations

import android.animation.ValueAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

class MainActivity : AppCompatActivity() {

    // Verifica se o menu está aberto
    var menuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val page = findViewById<ConstraintLayout>(R.id.page)
        val menu = findViewById<ImageView>(R.id.menu)

        /** Animação da janela **/
        val openMenu = ValueAnimator.ofFloat(0.91F, 0.75F)
        openMenu.interpolator = LinearInterpolator()
        openMenu.duration = 400
        openMenu.addUpdateListener { ValueAnimator ->
            val layoutParams = page.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.matchConstraintPercentHeight = ValueAnimator.animatedValue as Float
            page.layoutParams = layoutParams
        }
        val closeMenu = ValueAnimator.ofFloat(0.75F, 0.91F)
        closeMenu.interpolator = LinearInterpolator()
        closeMenu.duration = 400
        closeMenu.addUpdateListener { ValueAnimator ->
            val layoutParams = page.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.matchConstraintPercentHeight = ValueAnimator.animatedValue as Float
            page.layoutParams = layoutParams
        }

        menu.setOnClickListener {
            menuOpen = if(menuOpen) {
                // adiciona a animação do ícone
                menu.setImageDrawable(
                    AnimatedVectorDrawableCompat.create(
                        this,
                        R.drawable.anim_closetomenu
                    )
                )
                // inicia a animação da página
                closeMenu.start()
                false
            } else {
                // adiciona a animação do ícone
                menu.setImageDrawable(
                    AnimatedVectorDrawableCompat.create(
                        this,
                        R.drawable.anim_menutoclose
                    )
                )
                // inicia a animação da página
                openMenu.start()
                true
            }
            // Inicia a animação do ícone
            val animation = menu.drawable as AnimatedVectorDrawableCompat
            animation.start()
        }

    }

}