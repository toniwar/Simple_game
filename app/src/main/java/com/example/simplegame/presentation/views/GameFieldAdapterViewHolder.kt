package com.example.simplegame.presentation.views


import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.simplegame.R
import com.example.simplegame.databinding.GridCellBinding
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.NotEmptyCell
import kotlin.random.Random

class GameFieldAdapterViewHolder(private val view: View):
    RecyclerView.ViewHolder(view) {
    private val binding = GridCellBinding.bind(view)
        fun binds(width: Int?, gameCell: GameCell){
            width?.let {
                val paramsForLayout = FrameLayout.LayoutParams(width / 2, width / 2)
                val paramsForImageView = FrameLayout.LayoutParams((width / 2)-65 , (width / 2)-5 )
                binding.apply {
                    container.layoutParams = paramsForLayout
                    imageContainer.layoutParams = paramsForImageView
                }
            }


            if(gameCell is NotEmptyCell){
                when(gameCell.unit){
                    is Enemy -> binding.imageContainer.setImageResource(R.drawable.siren)
                    else -> binding.imageContainer.setImageResource(R.drawable.player)
                }

            }
            else{
                when(Random.nextInt(2)){
                    0 -> binding.imageContainer.setImageResource(R.drawable.ground_1)
                    else -> binding.imageContainer.setImageResource(R.drawable.ground_2)
                }

            }

        }
}