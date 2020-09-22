package per.freesky1102.easydiffadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by freesky1102 on 4/28/17.
 */
class MultipleTypeAdapter private constructor(
    private val viewTypeMap: Map<Int, ViewStrategy<BaseViewHolder<*>>>,
    callback: DiffUtil.ItemCallback<RowContainer<*, *>>,
    onChanged: ((position: Int, count: Int, payload: Any?) -> Unit)?,
    onMoved: ((fromPosition: Int, toPosition: Int) -> Unit)?,
    onInserted: ((position: Int, count: Int) -> Unit)?,
    onRemoved: ((position: Int, count: Int) -> Unit)?
) : RecyclerView.Adapter<BaseViewHolder<Any>>() {

    private val mHelper: AsyncListDiffer<RowContainer<*, *>> = AsyncListDiffer(
        CustomListUpdateCallBack(this, onChanged, onMoved, onInserted, onRemoved),
        AsyncDifferConfig.Builder(callback).build()
    )

    class Builder {

        private val viewTypeMap: MutableMap<Int, ViewStrategy<BaseViewHolder<*>>>

        private var onChanged: ((position: Int, count: Int, payload: Any?) -> Unit)? = null

        private var onMoved: ((fromPosition: Int, toPosition: Int) -> Unit)? = null

        private var onInserted: ((position: Int, count: Int) -> Unit)? = null

        private var onRemoved: ((position: Int, count: Int) -> Unit)? = null

        private var callback: DiffUtil.ItemCallback<RowContainer<*, *>> = EasyItemCallback()

        init {
            viewTypeMap = hashMapOf()
        }

        fun register(viewType: Int, strategy: ViewStrategy<*>): Builder {
            viewTypeMap[viewType] = strategy as ViewStrategy<BaseViewHolder<*>>
            return this
        }

        fun setOnChangedListener(onChanged: (position: Int, count: Int, payload: Any?) -> Unit): Builder {
            this.onChanged = onChanged
            return this
        }

        fun setOnMovedListener(onMoved: (fromPosition: Int, toPosition: Int) -> Unit): Builder {
            this.onMoved = onMoved
            return this
        }

        fun setOnInsertedListener(onInserted: (position: Int, count: Int) -> Unit): Builder {
            this.onInserted = onInserted
            return this
        }

        fun setOnRemovedListener(onRemoved: (position: Int, count: Int) -> Unit): Builder {
            this.onRemoved = onRemoved
            return this
        }

        fun setDiffCallback(callback: DiffUtil.ItemCallback<RowContainer<*, *>>): Builder {
            this.callback = callback
            return this
        }

        fun build(): MultipleTypeAdapter {
            val temp = viewTypeMap as Map<Int, ViewStrategy<BaseViewHolder<*>>>
            return MultipleTypeAdapter(temp, callback, onChanged, onMoved, onInserted, onRemoved)
        }
    }

    override fun getItemCount(): Int {
        return mHelper.currentList.size
    }

    operator fun get(index: Int): RowContainer<*, *>? {
        return mHelper.currentList[index]
    }

    override fun getItemViewType(position: Int): Int {
        val item = get(position)
        return item!!.viewType
    }

    fun <T> getOriginal(pos: Int): T {
        return mHelper.currentList[pos].original as T
    }

    fun submitList(list: List<RowContainer<*, *>>) {
        mHelper.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        val strategy = viewTypeMap.getValue(viewType)
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(strategy.getLayoutRes(parent.context), parent, false)
        return strategy.createVH.invoke(parent, view) as BaseViewHolder<Any>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        val item = get(position)
        val displayObject = item?.displayObject!!
        holder.bindView(displayObject)
    }
}