package per.freesky1102.easydiffadapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<O>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindView(item: O)

    protected fun context(): Context {
        return itemView.context.applicationContext
    }

    fun triggerAction(listener: OnActionItemListener?) {
        listener ?: return
        val pos = adapterPosition
        if (pos == RecyclerView.NO_POSITION) return

        listener.onAction(pos)
    }
}