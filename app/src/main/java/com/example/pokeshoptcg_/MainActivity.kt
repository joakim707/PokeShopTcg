package com.example.pokeshoptcg_

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupérer le NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Configuration des boutons de navigation
        findViewById<MaterialButton>(R.id.btnHome).setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }

        findViewById<MaterialButton>(R.id.btnProduct).setOnClickListener {
            navController.navigate(R.id.productFragment)
        }

        findViewById<MaterialButton>(R.id.btnFavorite).setOnClickListener {
            navController.navigate(R.id.favoriteFragment)
        }
    }
}