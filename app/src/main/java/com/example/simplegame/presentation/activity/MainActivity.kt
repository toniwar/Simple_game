package com.example.simplegame.presentation.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplegame.R
import com.example.simplegame.presentation.fragments.GameWindow
import com.example.simplegame.presentation.fragments.LoseFragment
import com.example.simplegame.presentation.fragments.MainFragment
import com.example.simplegame.presentation.fragments.VictoryFragment
import com.example.simplegame.presentation.view_models.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var vm: MainViewModel
    private val mainFragment = MainFragment()
    private val victoryFragment = VictoryFragment()
    private val loseFragment = LoseFragment()
    private lateinit var scoreTV:TextView

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreTV = findViewById(R.id.game_score_tv)

        vm = ViewModelProvider(this)[MainViewModel::class.java]

        vm.data.observe(this){
            when(it){
                "Home" -> openFragment(mainFragment)
                "Game" -> {
                    openFragment(GameWindow())
                }
                "Victory" -> openFragment(victoryFragment)
                "Lose" -> openFragment(loseFragment)
                else -> scoreTV.text = it
            }
        }

        vm.sendData("Home")

    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.top_fragment, fragment)
            .commit()
    }


}
