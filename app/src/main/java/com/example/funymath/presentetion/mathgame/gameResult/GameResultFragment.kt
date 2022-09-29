package com.example.funymath.presentetion.mathgame.gameResult

import com.example.funymath.appbase.FragmentBaseMVVM
import com.example.funymath.appbase.utils.viewBinding
import com.example.funymath.databinding.FragmentGameResultBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameResultFragment : FragmentBaseMVVM<GameResultViewModel, FragmentGameResultBinding>() {
    override val viewModel: GameResultViewModel by viewModel()
    override val binding: FragmentGameResultBinding by viewBinding()

}