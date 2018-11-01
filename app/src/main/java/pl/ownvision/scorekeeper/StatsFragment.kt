package pl.ownvision.scorekeeper

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.activityViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import pl.ownvision.scorekeeper.core.BaseEpoxyFragment
import pl.ownvision.scorekeeper.core.simpleController
import pl.ownvision.scorekeeper.views.viewChart
import pl.ownvision.scorekeeper.views.viewLoader
import pl.ownvision.scorekeeper.views.viewNoData

class StatsFragment : BaseEpoxyFragment() {

    private val viewModel : GameViewModel by activityViewModel(GameViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideFabButton()
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        if (!state.timeline.complete) {
            viewLoader {
                id("loader")
            }
            return@simpleController
        }
        val timeline = state.timeline()
        if (timeline.isNullOrEmpty()){
            viewNoData {
                id("viewNoPlayer")
                information(R.string.no_moves_information)
            }
            return@simpleController
        }

        val moves = timeline.groupBy { it.playerName }

        val colors = requireActivity().resources.getIntArray(R.array.rainbow)
        val lineDataSets = mutableListOf<LineDataSet>()
        var colorCounter = 0
        moves.forEach { move ->
            val entries = mutableListOf<Entry>()
            var counter = 1
            var sum = 0
            val sortedMoves = move.value.sortedBy { it.createdAt }
            sortedMoves.forEach {
                sum += it.score
                entries.add(Entry(counter.toFloat(), sum.toFloat()))
                counter++
            }
            val lineDataSet = LineDataSet(entries, move.key)
            val color = colors[colorCounter.rem(colors.size)]
            lineDataSet.color = color
            lineDataSet.valueTextColor = color
            lineDataSets.add(lineDataSet)
            colorCounter++
        }

        viewChart {
            id("chart")
            lineData(lineDataSets)
        }
    }
}