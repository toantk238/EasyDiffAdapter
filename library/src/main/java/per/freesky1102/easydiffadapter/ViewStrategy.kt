package per.freesky1102.easydiffadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KClass

open class ViewStrategy<V : BaseViewHolder<*>>(
    internal val vClass: KClass<V>,
    internal var createVH: (parent: ViewGroup, itemView: View) -> V
) {

    private var layoutRes: Int = 0

    fun getLayoutRes(context: Context): Int {
        if (layoutRes != 0) return layoutRes

        val layoutResAnnotation = findLayout(vClass)
        val layoutResText = layoutResAnnotation.layout
        layoutRes = context.resources.getIdentifier(layoutResText, "layout", context.packageName)
        return layoutRes
    }

    private fun findLayout(vClass: KClass<*>): LayoutInVH {

        var temp: Class<*>? = vClass.java
        var annotation: LayoutInVH? = null

        while (temp != null) {
            annotation = temp.getAnnotation(LayoutInVH::class.java)
            if (annotation != null) break
            temp = temp.superclass
        }

        if (annotation == null) {
            throw IllegalArgumentException("${vClass.java.canonicalName} requires LayoutInVH annotation")
        }

        return annotation
    }
}