package eg.com.invive.kotlin_first.Utils

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class ItemDecoration(private val spanCount:Int,private val space:Int,private val displayWith:Int,private val cardWith:Int):
    RecyclerView.ItemDecoration() {

companion object{
    fun dpToPx( dp:Int, resources: Resources):Int {
        val r = resources;
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics).roundToInt();
    }
}

    var space2=0

    init {
        val perfectSpacing=displayWith-(cardWith*spanCount);
        space2=perfectSpacing/(spanCount+1)
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
       val position=parent.getChildAdapterPosition(view)
        val column=position%spanCount

        if (column % 2 != 0) {
            outRect.left = space2 / 2
            outRect.right = space2 / 2
        } else {
            outRect.left = space2
            outRect.right = space2
        }

        outRect.top=space

    }
}