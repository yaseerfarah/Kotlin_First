package eg.com.invive.kotlin_first.Interface

interface OnItemClicked<in T> {
    fun onClick(obj:T)
}