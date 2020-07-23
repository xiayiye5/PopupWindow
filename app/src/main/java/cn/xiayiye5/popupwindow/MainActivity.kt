package cn.xiayiye5.popupwindow

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * @author XiaYiYe5
 * 2020年7月23日15:58:09
 * 开发分支
 * 用于区分Java和Kotlin版本的首页
 */
class MainActivity : AppCompatActivity() {
    private var context: Context? = null
    private var popupWindow: PopupWindow? = null
    private var from = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_main)
        val popLeftBtn =
            findViewById<View>(R.id.pop_left_btn) as Button
        val popRightBtn =
            findViewById<View>(R.id.pop_right_btn) as Button
        val popBottomBtn =
            findViewById<View>(R.id.pop_bottom_btn) as Button
        popLeftBtn.setOnClickListener(popClick)
        popRightBtn.setOnClickListener(popClick)
        popBottomBtn.setOnClickListener(popClick)
    }

    var popClick = View.OnClickListener { v ->
        when (v.id) {
            R.id.pop_left_btn -> {
                from = Location.LEFT.ordinal
            }
            R.id.pop_right_btn -> {
                from = Location.RIGHT.ordinal
            }
            R.id.pop_bottom_btn -> {
                from = Location.BOTTOM.ordinal
            }
            else -> {
            }
        }

        //调用此方法，menu不会顶置
        //popupWindow.showAsDropDown(v);
        initPopupWindow()
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    internal inner class popupDismissListener : PopupWindow.OnDismissListener {
        override fun onDismiss() {
            backgroundAlpha(1f)
        }
    }

    protected fun initPopupWindow() {
        val popupWindowView = layoutInflater.inflate(R.layout.pop, null)
        //内容，高度，宽度
        popupWindow = if (Location.BOTTOM.ordinal == from) {
            PopupWindow(
                popupWindowView,
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )
        } else {
            PopupWindow(
                popupWindowView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.FILL_PARENT,
                true
            )
        }
        //动画效果
        if (Location.LEFT.ordinal == from) {
            popupWindow!!.animationStyle = R.style.AnimationLeftFade
        } else if (Location.RIGHT.ordinal == from) {
            popupWindow!!.animationStyle = R.style.AnimationRightFade
        } else if (Location.BOTTOM.ordinal == from) {
            popupWindow!!.animationStyle = R.style.AnimationBottomFade
        }
        //菜单背景色
        val dw = ColorDrawable(-0x1)
        popupWindow!!.setBackgroundDrawable(dw)
        //宽度
        //popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        //高度
        //popupWindow.setHeight(LayoutParams.FILL_PARENT);
        //显示位置
        if (Location.LEFT.ordinal == from) {
            popupWindow!!.showAtLocation(
                layoutInflater.inflate(R.layout.activity_main, null),
                Gravity.LEFT,
                0,
                500
            )
        } else if (Location.RIGHT.ordinal == from) {
            popupWindow!!.showAtLocation(
                layoutInflater.inflate(R.layout.activity_main, null),
                Gravity.RIGHT,
                0,
                500
            )
        } else if (Location.BOTTOM.ordinal == from) {
            popupWindow!!.showAtLocation(
                layoutInflater.inflate(R.layout.activity_main, null),
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                0,
                0
            )
        }
        //设置背景半透明
        backgroundAlpha(0.5f)
        //关闭事件
        popupWindow!!.setOnDismissListener(popupDismissListener())
        popupWindowView.setOnTouchListener { v, event -> false }
        val open =
            popupWindowView.findViewById<View>(R.id.open) as Button
        val save =
            popupWindowView.findViewById<View>(R.id.save) as Button
        val close =
            popupWindowView.findViewById<View>(R.id.close) as Button
        open.setOnClickListener {
            Toast.makeText(context, "Open", Toast.LENGTH_LONG).show()
            popupWindow!!.dismiss()
            startActivity(Intent(this, HomeActivity::class.java))
        }
        save.setOnClickListener {
            Toast.makeText(context, "Open", Toast.LENGTH_LONG).show()
            popupWindow!!.dismiss()
        }
        close.setOnClickListener {
            Toast.makeText(context, "Open", Toast.LENGTH_LONG).show()
            popupWindow!!.dismiss()
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    fun backgroundAlpha(bgAlpha: Float) {
        val lp = window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        window.attributes = lp
    }

    /**
     * 菜单弹出方向
     */
    enum class Location {
        LEFT, RIGHT, TOP, BOTTOM
    }
}