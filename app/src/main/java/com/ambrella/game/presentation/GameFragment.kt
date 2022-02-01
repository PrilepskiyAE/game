package com.ambrella.game.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.*
import com.ambrella.game.R
import com.ambrella.game.databinding.FragmentGameBinding
import com.ambrella.game.domain.entity.GameResult
import com.ambrella.game.domain.entity.GameSettings
import com.ambrella.game.domain.entity.Level


class GameFragment : Fragment() {
    private lateinit var level: Level
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }
    private val tvOption by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)


        }
    }
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding==null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.startGame(level)
        setClickLisenersToOption()

    }

    private fun setClickLisenersToOption(){
        for (tvOpt in tvOption){
           tvOpt.setOnClickListener {
               viewModel.chooseAnswer(tvOpt.text.toString().toInt())
           }
        }
    }

    private fun observeViewModel(){
        viewModel.question.observe(viewLifecycleOwner){
            binding.tvSum.text=it.sum.toString()
            binding.tvLeftNumber.text=it.visibleNumber.toString()
            for(i in 0 until  tvOption.size )
            {
                tvOption[i].text= it.option[i].toString()
            }
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it,true)
        }
        viewModel.enoughCount.observe(viewLifecycleOwner){
            val color = getColorByState(it)
            binding.tvAnswersProgress.setTextColor(color)
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner){
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.tvTimer.text=it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress=it
        }
viewModel.gameResult.observe(viewLifecycleOwner){
    launchGameFinishFragment(it)
}
        viewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }

    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        val color = ContextCompat.getColor(requireContext(), colorResId)
        return color
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs(){
         requireArguments().getParcelable<Level>(KEY_LEVEL) ?.let {
             level=it
         }
    }

    private fun launchGameFinishFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstnce(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object{
        const val NAME = "GameFragment"
        private const val KEY_LEVEL="level"
        fun newInstance(level: Level):GameFragment{
            return GameFragment().apply {
              arguments=Bundle().apply {
                  putParcelable(KEY_LEVEL,level)
              }
            }
        }
    }

}