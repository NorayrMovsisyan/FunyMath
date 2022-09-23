package com.example.funymath.presentetion.mathgame

import com.example.funymath.appbase.utils.viewBinding
import com.example.funymath.appbase.viewmodel.BaseViewModel
import com.example.funymath.databinding.FragmentWelcomeBinding
import droid.telemed.mts.ru.telemed.refactor.appbase.FragmentBaseMVVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : FragmentBaseMVVM<BaseViewModel, FragmentWelcomeBinding>() {
    override val viewModel: BaseViewModel by viewModel()
    override val binding: FragmentWelcomeBinding by viewBinding()

}