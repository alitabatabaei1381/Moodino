package com.iranmobiledev.moodino.ui.states

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentStatsBinding


class StatsFragment : BaseFragment() {
    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val model :StatsFragmentViewModel by viewModels()

        model.initializeLineChart(binding.moodChartCardInclude.moodsLineChart,requireContext())

//        val greyFilter = PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
//        myLayout.getBackground().setColorFilter(greyFilter)
//        myImageView.setColorFilter(greyFilter)
//        myTextView.setTextColor(-0x888889)
    }
}