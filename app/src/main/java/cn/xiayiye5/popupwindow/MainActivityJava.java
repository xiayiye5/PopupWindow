package cn.xiayiye5.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * @author Administrator
 * Java页面
 */
public class MainActivityJava extends Activity {
    private Context context = null;
    private PopupWindow popupWindow;
    private int from = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        Button popLeftBtn = (Button) findViewById(R.id.pop_left_btn);
        Button popRightBtn = (Button) findViewById(R.id.pop_right_btn);
        Button popBottomBtn = (Button) findViewById(R.id.pop_bottom_btn);
        popLeftBtn.setOnClickListener(popClick);
        popRightBtn.setOnClickListener(popClick);
        popBottomBtn.setOnClickListener(popClick);
    }


    View.OnClickListener popClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_left_btn: {
                    from = Location.LEFT.ordinal();
                    break;
                }
                case R.id.pop_right_btn: {
                    from = Location.RIGHT.ordinal();
                    break;
                }
                case R.id.pop_bottom_btn: {
                    from = Location.BOTTOM.ordinal();
                    break;
                }
                default:
                    break;
            }

            //调用此方法，menu不会顶置
            //popupWindow.showAsDropDown(v);
            initPopupWindow();

        }
    };

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }


    protected void initPopupWindow() {
        View popupWindowView = getLayoutInflater().inflate(R.layout.pop, null);
        //内容，高度，宽度
        if (Location.BOTTOM.ordinal() == from) {
            popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        } else {
            popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT, true);
        }
        //动画效果
        if (Location.LEFT.ordinal() == from) {
            popupWindow.setAnimationStyle(R.style.AnimationLeftFade);
        } else if (Location.RIGHT.ordinal() == from) {
            popupWindow.setAnimationStyle(R.style.AnimationRightFade);
        } else if (Location.BOTTOM.ordinal() == from) {
            popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        }
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        //宽度
        //popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        //高度
        //popupWindow.setHeight(LayoutParams.FILL_PARENT);
        //显示位置
        if (Location.LEFT.ordinal() == from) {
            popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.LEFT, 0, 500);
        } else if (Location.RIGHT.ordinal() == from) {
            popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.RIGHT, 0, 500);
        } else if (Location.BOTTOM.ordinal() == from) {
            popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        //设置背景半透明
        backgroundAlpha(0.5f);
        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
				/*if( popupWindow!=null && popupWindow.isShowing()){
					popupWindow.dismiss();
					popupWindow=null;
				}*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

        Button open = (Button) popupWindowView.findViewById(R.id.open);
        Button save = (Button) popupWindowView.findViewById(R.id.save);
        Button close = (Button) popupWindowView.findViewById(R.id.close);


        open.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Open", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Open", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Open", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 菜单弹出方向
     */
    public enum Location {LEFT, RIGHT, TOP, BOTTOM;}
}
