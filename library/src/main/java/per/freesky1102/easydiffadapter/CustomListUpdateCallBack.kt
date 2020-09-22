package per.freesky1102.easydiffadapter

import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

class CustomListUpdateCallBack(
    val adapter: RecyclerView.Adapter<*>,
    private val onChanged: ((position: Int, count: Int, payload: Any?) -> Unit)?,
    private val onMoved: ((fromPosition: Int, toPosition: Int) -> Unit)?,
    private val onInserted: ((position: Int, count: Int) -> Unit)?,
    private val onRemoved: ((position: Int, count: Int) -> Unit)?
) : ListUpdateCallback {

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapter.notifyItemRangeChanged(position, count, payload)
        onChanged?.invoke(position, count, payload)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition, toPosition)
        onMoved?.invoke(fromPosition, toPosition)
    }

    override fun onInserted(position: Int, count: Int) {
        adapter.notifyItemRangeInserted(position, count)
        onInserted?.invoke(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.notifyItemRangeRemoved(position, count)
        onRemoved?.invoke(position, count)
    }
}