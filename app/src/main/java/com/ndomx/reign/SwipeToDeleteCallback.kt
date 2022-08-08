package com.ndomx.reign

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(
    private val context: Context,
    private val onSwipeCallback: (Int) -> Unit
) : ItemTouchHelper.Callback() {
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#b80f0a")
    private val clearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        val isCancelled = ((dX == 0f) && (!isCurrentlyActive))

        if (isCancelled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top + 0f,
                itemView.right + 0f,
                itemView.bottom + 0f
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        background.color = backgroundColor
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)

        ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_outline_24)?.let { drawable ->
            val iconMargin = (itemHeight - drawable.intrinsicHeight) / 2

            val iconTop = itemView.top + iconMargin
            val iconRight = itemView.right - iconMargin
            val iconLeft = iconRight - drawable.intrinsicWidth
            val iconBottom = iconTop + drawable.intrinsicHeight

            drawable.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            drawable.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipeCallback(viewHolder.bindingAdapterPosition)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, clearPaint)
    }
}