package per.freesky1102.easydiffadapter

/**
 * Avoid compare the original because between 2 rows, we only care about type and how it displays.
 */
data class RowContainer<O, S> constructor(val viewType: Int, val displayObject: S) {

    var original: O? = null

    constructor(viewType: Int, original: O, displayObject: S) : this(viewType, displayObject) {
        this.original = original
    }
}