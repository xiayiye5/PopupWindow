package cn.xiayiye5.popupwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

/**
 * @author xiayiye5
 * 2020年6月11日16:06:18
 * 底部弹出的PopupWindow
 */
public class HomeActivity extends Activity implements View.OnClickListener {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void showPop(View view) {
        View popupWindowLayout = getLayoutInflater().inflate(R.layout.activity_pop, null, false);
        RelativeLayout rlTitleClose = popupWindowLayout.findViewById(R.id.rl_title_close);
        rlTitleClose.setOnClickListener(this);
        popupWindow = new PopupWindow(popupWindowLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        //菜单背景色
//        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.half_circular_bg));
        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_home, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //设置背景半透明
        backgroundAlpha(0.5f);
        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());
    }

    @Override
    public void onClick(View v) {
        //关闭PopupWindow
        popupWindow.dismiss();
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //取值：0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
