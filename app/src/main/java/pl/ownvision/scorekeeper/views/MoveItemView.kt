package pl.ownvision.scorekeeper.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import kotlinx.android.synthetic.main.item_move_layout.view.*
import pl.ownvision.scorekeeper.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MoveItemView  @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.item_move_layout, this)
    }

    @TextProp
    fun setTitle(title: CharSequence) {
        tvMoveTitle.text = title
    }

    @TextProp
    fun setCreatedAt(moveCount: CharSequence) {
        tvMoveCreatedAt.text = moveCount
    }

    @CallbackProp
    fun onOptionsClick(listener: View.OnClickListener?) {
        textViewOptions.setOnClickListener(listener)
    }
}