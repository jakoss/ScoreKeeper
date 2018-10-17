package pl.ownvision.scorekeeper.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import kotlinx.android.synthetic.main.view_no_player.view.*
import pl.ownvision.scorekeeper.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class ViewNoPlayer @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_no_player, this)
    }

    @CallbackProp
    fun onClick(listener: View.OnClickListener?) {
        score_add_new_player.setOnClickListener(listener)
    }
}