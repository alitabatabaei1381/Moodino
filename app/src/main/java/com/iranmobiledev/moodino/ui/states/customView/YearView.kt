package com.iranmobiledev.moodino.ui.states.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.core.content.res.ResourcesCompat
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.utlis.ColorArray
import com.iranmobiledev.moodino.utlis.EmojiValue
import saman.zamani.persiandate.PersianDate
import kotlin.math.roundToInt

class YearView(context: Context, attr: AttributeSet? = null) : View(context, attr) {

    var entries: List<Entry> = listOf()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var canvas: Canvas? = null
    private var ySpace = resources.getDimension(R.dimen.ySpace)
    private var xSpace = resources.getDimension(R.dimen.xSpace)
    private var margin = resources.getDimension(R.dimen.margin)
    private val persianDate = PersianDate()
    private val grayColor = resources.getColor(R.color.gray_icon)
    private var isFirstTimeDraw = false
    private val monthsLength = listOf<Int>(
        persianDate.getMonthLength(persianDate.shYear, 1),
        persianDate.getMonthLength(persianDate.shYear, 2),
        persianDate.getMonthLength(persianDate.shYear, 3),
        persianDate.getMonthLength(persianDate.shYear, 4),
        persianDate.getMonthLength(persianDate.shYear, 5),
        persianDate.getMonthLength(persianDate.shYear, 6),
        persianDate.getMonthLength(persianDate.shYear, 7),
        persianDate.getMonthLength(persianDate.shYear, 8),
        persianDate.getMonthLength(persianDate.shYear, 9),
        persianDate.getMonthLength(persianDate.shYear, 10),
        persianDate.getMonthLength(persianDate.shYear, 11),
        persianDate.getMonthLength(persianDate.shYear, 12),
    )
    private val monthsName = listOf(
        persianDate.setShMonth(1).monthName().elementAt(0),
        persianDate.setShMonth(2).monthName().elementAt(0),
        persianDate.setShMonth(3).monthName().elementAt(0),
        persianDate.setShMonth(4).monthName().elementAt(0),
        persianDate.setShMonth(5).monthName().elementAt(0),
        persianDate.setShMonth(6).monthName().elementAt(0),
        persianDate.setShMonth(7).monthName().elementAt(0),
        persianDate.setShMonth(8).monthName().elementAt(0),
        persianDate.setShMonth(9).monthName().elementAt(0),
        persianDate.setShMonth(10).monthName().elementAt(0),
        persianDate.setShMonth(11).monthName().elementAt(0),
        persianDate.setShMonth(12).monthName().elementAt(0),
    )

    fun setData() {
        val radius = (width / context.resources.displayMetrics.density) / 12 - margin
        drawMonths(radius, entries)
        drawMonthDaysNumberColumn(radius)
        if (!isFirstTimeDraw){
            postInvalidate()
            isFirstTimeDraw = true
        }
    }

    private fun drawMonthDaysNumberColumn(radius: Float) {
        for (i in 1..31) {
            val index = if (i == 0) 1 else i
            drawNumber(
                i.toString(),
                radius,
                (radius * (index * 2.5) + ySpace + resources.getDimension(R.dimen.margin1)).toFloat()
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.canvas = canvas
        setData()
    }

    private fun drawCircle(cx: Float, cy: Float, radius: Float, color: Int) {
        paint.color = color
        canvas?.drawCircle(cx, cy, radius, paint)
    }

    private fun drawNumber(text: String, x: Float, y: Float) {
        paint.color = grayColor
        paint.typeface = ResourcesCompat.getFont(context, R.font.shabnam_medium)
        paint.textSize = resources.getDimension(R.dimen.textSize)
        paint.textAlign = Paint.Align.CENTER

        canvas?.drawText(text, x, y, paint)
    }

    private fun drawText(text: String, x: Float, y: Float) {
        paint.color = grayColor
        paint.textSize = resources.getDimension(R.dimen.textSize)
        paint.typeface = ResourcesCompat.getFont(context, R.font.shabnam_medium)
        paint.textAlign = Paint.Align.CENTER
        canvas?.drawText(text, x, y, paint)
    }

    private fun drawMonths(radius: Float, entries: List<Entry>) {
        monthsLength.forEachIndexed { index, length ->
            drawMonth(index + 1, length, radius)
        }
    }

    private fun drawMonth(month: Int, length: Int, radius: Float) {
        for (i in 1..length) {
            val index = if (i == 0) 1 else i

            val entryList = entries.filter {it.date.year == PersianDate().shYear && it.date.month == month && it.date.day == i}

            val color =
                if (entryList.isNotEmpty()) getMoodAverageColor(entryList) else grayColor

            if (i == 1) {
                drawText(
                    monthsName[month - 1].toString(),
                    radius * (month * 3.2).toFloat(),
                    radius + resources.getDimension(R.dimen.margin)
                )
            }

            drawCircle(
                cx = (radius * (month * 3.2)).toFloat(),
                (radius * (index * 2.5) + ySpace).toFloat(),
                radius,
                color
            )
        }
    }

    fun getMoodAverageColor(moodsValue:List<Entry>): Int {
        val moods = arrayListOf<Int>()
        moodsValue.forEach {
            moods.add(it.emojiValue)
        }

        return  getColor(moods.average().roundToInt())
    }

    fun getColor(emojiValue: Int):Int = when(emojiValue) {
        1 -> ColorArray.awful
        2 -> ColorArray.bad
        3 -> ColorArray.meh
        4 -> ColorArray.good
        5 -> ColorArray.rad
        else -> {ColorArray.meh}
    }
}