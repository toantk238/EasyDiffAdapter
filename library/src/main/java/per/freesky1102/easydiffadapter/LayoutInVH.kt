package per.freesky1102.easydiffadapter

/**
 * Have to use layoutRes as String, can't be Int.
 * Ref : https://github.com/androidannotations/androidannotations/issues/20
 */
@Target(AnnotationTarget.CLASS)
annotation class LayoutInVH(val layout: String)