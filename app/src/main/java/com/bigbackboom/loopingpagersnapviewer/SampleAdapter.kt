package com.bigbackboom.loopingpagersnapviewer

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.bigbackboom.loopingpagersnapviewer.databinding.LayoutItemBinding
import timber.log.Timber

class SampleAdapter : RecyclerView.Adapter<BindingHolder<*>>() {

    private val list = mutableListOf<String>("loading...1", "loading...2", "loading...3")
    lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<*> {
        val context = parent.context ?: run { throw NullPointerException("parent context null") }
        return BindingHolder<LayoutItemBinding>(context, parent, R.layout.layout_item)
    }

    override fun onBindViewHolder(holder: BindingHolder<*>, position: Int) {
        Timber.d("pos=$position, ofspos=${position % list.size} msg=${list[position % list.size]}")
        (holder.binding as LayoutItemBinding).message =
                "ofspos=${position % list.size} msg=${list[position % list.size]}"
    }

    override fun getItemCount(): Int = list.size * 3

    fun getListSize() = list.size

    fun addList(models: List<String>) {
        list.addAll(models)
        recyclerView.smoothScrollToPosition(list.size + 2)
        notifyDataSetChanged()
    }

    fun resetPosition() {
        recyclerView.smoothScrollToPosition(list.size)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    class OnScrollListener(val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
            val itemCount = (recyclerView.adapter as SampleAdapter).getListSize()

            if (firstItemVisible > itemCount && firstItemVisible % itemCount == 0) {
                // リストの最後尾にたどり着いたら、最初の要素に戻る
                recyclerView.scrollToPosition(itemCount)
            } else if (firstItemVisible == itemCount - 1) {
                // リストの最前列にたどり着いたら、最後尾に戻る
                recyclerView.scrollToPosition(itemCount * 2)
            }
        }

    }
}
