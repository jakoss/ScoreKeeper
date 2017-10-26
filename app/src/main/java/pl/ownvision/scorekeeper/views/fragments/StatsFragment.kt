package pl.ownvision.scorekeeper.views.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.metalab.asyncawait.async
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.fragment_stats_list.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.viewmodels.GameViewModel


class StatsFragment : BaseGameFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(GameViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_stats_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart.description.text = ""
        chart.xAxis.setDrawLabels(false)
        chart.xAxis.setDrawGridLines(false)
    }

    override fun onStart() {
        super.onStart()
        async {
            chart.data = await { viewModel.getTimeline() }
            chart.invalidate()
        }
    }
}
