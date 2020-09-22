package per.freesky1102.easydiffadapter

import androidx.recyclerview.widget.DiffUtil

class EasyItemCallback<T>(
    private val sameItems: ((oldItem: T, newItem: T) -> Boolean)? = null,
    private val sameContents: ((oldItem: T, newItem: T) -> Boolean)? = null
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        sameItems ?: return oldItem == newItem
        return sameItems.invoke(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        sameContents ?: return oldItem == oldItem
        return sameContents.invoke(oldItem, newItem)
    }
}