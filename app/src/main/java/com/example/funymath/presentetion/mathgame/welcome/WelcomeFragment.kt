package com.example.funymath.presentetion.mathgame.welcome

import com.example.funymath.appbase.utils.viewBinding
import com.example.funymath.databinding.FragmentWelcomeBinding
import com.example.funymath.appbase.FragmentBaseMVVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class WelcomeFragment : FragmentBaseMVVM<WelcomeViewModel, FragmentWelcomeBinding>() {
    override val viewModel: WelcomeViewModel by viewModel()
    override val binding: FragmentWelcomeBinding by viewBinding()

    override fun onViewClick() {
        binding.next.setOnClickListener {
            navigateFragment(WelcomeFragmentDirections.actionWelcomeFragmentToSelectLevelFragment())
        }
    }

}