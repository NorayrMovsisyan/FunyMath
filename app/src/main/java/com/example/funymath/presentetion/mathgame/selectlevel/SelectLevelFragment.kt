package com.example.funymath.presentetion.mathgame.selectlevel

import com.example.funymath.appbase.FragmentBaseMVVM
import com.example.funymath.appbase.utils.viewBinding
import com.example.funymath.data.model.Level
import com.example.funymath.databinding.FragmentSelectLevelBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectLevelFragment : FragmentBaseMVVM<SelectLevelViewModel, FragmentSelectLevelBinding>() {
    override val viewModel: SelectLevelViewModel by viewModel()
    override val binding: FragmentSelectLevelBinding by viewBinding()
    override fun onViewClick() {
        with(binding) {
            test.setOnClickListener {
                navigateFragment(
                    SelectLevelFragmentDirections.actionSelectLevelFragmentToMathGameFragment(
                        Level.Test
                    )
                )

            }
            easy.setOnClickListener {
                navigateFragment(
                    SelectLevelFragmentDirections.actionSelectLevelFragmentToMathGameFragment(
                        Level.Easy
                    )
                )
            }
            normal.setOnClickListener {
                navigateFragment(
                    SelectLevelFragmentDirections.actionSelectLevelFragmentToMathGameFragment(
                        Level.Normal
                    )
                )
            }
            hard.setOnClickListener {
                navigateFragment(
                    SelectLevelFragmentDirections.actionSelectLevelFragmentToMathGameFragment(
                        Level.Hard
                    )
                )
            }

        }
    }
}