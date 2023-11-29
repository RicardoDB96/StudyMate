package com.domberdev.studymate.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.domberdev.studymate.R
import com.domberdev.studymate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = getColor(R.color.background)

        initUI()
    }

    private fun initUI() {
        initNavigation()
        changeToolbarTitle()
        changeToolbarIcon()
        changeNavigationBarColor()
        binding.toolbarMain.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    private fun initNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
    }

    private fun changeToolbarTitle() {
        // Observar cambios en el NavController para actualizar el título del Toolbar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Cambiar el título del Toolbar según el fragmento actual
            binding.toolbarMain.title = destination.label
        }
    }

    private fun changeToolbarIcon() {
        // Observar cambios en el NavController para actualizar el icono del Toolbar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Según la id
            when (destination.id) {
                R.id.homeFragment -> binding.toolbarMain.navigationIcon = null
                R.id.addFragment -> binding.toolbarMain.navigationIcon =
                    AppCompatResources.getDrawable(this, R.drawable.ic_close)

                R.id.detailFragment -> binding.toolbarMain.navigationIcon =
                    AppCompatResources.getDrawable(this, R.drawable.ic_back)
            }
        }
    }

    private fun changeNavigationBarColor() {
        // Observar cambios en el NavController para actualizar el título del Toolbar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Según la id
            val handler = Handler(Looper.getMainLooper())
            when (destination.id) {
                // Retrasamos el cambiar el valor por 400 ms para sincronizar con la animación de salida
                R.id.homeFragment -> handler.postDelayed({
                    window.navigationBarColor = getColor(R.color.background)
                }, 400)

                R.id.addFragment -> window.navigationBarColor = getColor(R.color.grey)
            }
        }
    }

    //Función que oculta el teclado, al mismo tiempo que quita el foco a los TextFields.
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus?.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}