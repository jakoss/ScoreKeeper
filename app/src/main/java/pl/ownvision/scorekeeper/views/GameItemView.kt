package pl.ownvision.scorekeeper.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import kotlinx.android.synthetic.main.item_game_layout.view.*
import pl.ownvision.scorekeeper.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class GameItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.item_game_layout, this)
    }

    @TextProp
    fun setName(name: CharSequence) {
        tvName.text = name
    }

    @TextProp
    fun setCreatedAt(createdAt: CharSequence) {
        tvCreatedAt.text = createdAt
    }

    @CallbackProp
    fun onOptionsClick(listener: View.OnClickListener?) {
        textViewOptions.setOnClickListener(listener)
    }

    @CallbackProp
    fun onItemClick(listener: OnClickListener?) {
        card_view.setOnClickListener(listener)
    }
}