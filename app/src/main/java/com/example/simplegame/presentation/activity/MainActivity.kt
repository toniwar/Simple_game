package com.example.simplegame.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.simplegame.R
import com.example.simplegame.presentation.fragments.GameWindow
import com.example.simplegame.presentation.fragments.LoseFragment
import com.example.simplegame.presentation.fragments.VictoryFragment
import com.example.simplegame.presentation.view_models.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var vm: MainViewModel
    private val gameFragment = GameWindow()
    private val victoryFragment = VictoryFragment()
    private val loseFragment = LoseFragment()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this)[MainViewModel::class.java]

        vm.data.observe(this){
            when(it){
                "Game" -> openFragment(gameFragment)
                "Victory" -> openFragment(victoryFragment)
                "Lose" -> openFragment(loseFragment)
            }
        }

        vm.sendData("Game")

    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_layout, fragment)
            .commit()
    }


}
