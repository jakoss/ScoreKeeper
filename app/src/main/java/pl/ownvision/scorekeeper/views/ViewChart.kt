package pl.ownvision.scorekeeper.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.view_chart.view.*
import pl.ownvision.scorekeeper.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class ViewChart @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_chart, this)
        chart.description.text = ""
        chart.xAxis.setDrawLabels(false)
        chart.xAxis.setDrawGridLines(false)
    }

    @ModelProp
    fun setLineData(lineData: List<LineDataSet>) {
        chart.data = LineData(lineData)
        chart.invalidate()
    }
}