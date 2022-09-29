package com.example.funymath.presentetion.mathgame.mathgame

import androidx.navigation.fragment.navArgs
import com.example.funymath.appbase.FragmentBaseMVVM
import com.example.funymath.appbase.utils.viewBinding
import com.example.funymath.databinding.FragmentMathGameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MathGameFragment : FragmentBaseMVVM<MathGameViewModel, FragmentMathGameBinding>() {
    override val viewModel: MathGameViewModel by viewModel()
    override val binding: FragmentMathGameBinding by viewBinding()
    private val args: MathGameFragmentArgs by navArgs()

    private val numberList by lazy {
        listOf(
            binding.option1,
            binding.option2,
            binding.option3,
            binding.option4,
            binding.option5,
            binding.option6

        )
    }

    override fun onView() {
        viewModel.setLevel(args.level)
        viewModel.endGame()
        setOnclickListener()

        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.timer.text = it
        }
        viewModel.questions.observe(viewLifecycleOwner){
            if (!it?.options.isNullOrEmpty()) {
                binding.leftDig.text = it?.visibleNumber.toString()
                binding.sum.text = it?.sum.toString()
                numberList.forEachIndexed { index, button ->
                    button.text = it?.options?.get(index)?.toString() ?: ""
                }
            }
        }
        viewModel.percentOfRightAnswer.observe(viewLifecycleOwner){
            binding.progress.setProgress(it, true)
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            popBackStack()
        }
    }

    private fun setOnclickListener() {
        numberList.forEach { text ->
            text.setOnClickListener {
                viewModel.chooseAnswer(text.text.toString().toInt())
            }
        }
    }
}