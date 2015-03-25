package rubilov.eugeniy.prettyseekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.SeekBar;

/**
 * Created by eugeniy.rubilov (e.rubilov@gmail.com) on 18.03.2015.
 */
public class BubbleSeekBar extends SeekBar {
    private static final String TAG = BubbleSeekBar.class.getSimpleName();

    /**
     * popup window if seek bar changing
     */
    private PopupWindow mBubble;

    /**
     * main container of popup window {@link PopupWindow}
     * example: getWindow().getDecorView();
     */
    private View parent;

    /**
     * SeekBar location on screen
     */
    private int [] mLocationOnScreen = new int[2];

    /**
     * offset delta for popup window
     * by default tip has center Gravity
     */
    private float mGravityDelta = 0.5f;

    /**
     * space by X and Y between SeekBar and PopupWindow
     */
    protected int userOffsetX, userOffsetY;


    public BubbleSeekBar(Context context) {
        super(context);
    }

    public BubbleSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set popup window gravity
     * @param gravity - gravity value
     */
    public void setBubbleGravity(int gravity){

        switch (gravity){
            case Gravity.LEFT: {
                mGravityDelta = 1;
            }break;

            case Gravity.RIGHT: {
                mGravityDelta = 0;
            }break;
            case Gravity.CENTER: {
                mGravityDelta = 0.5f;
            }break;
            default: mGravityDelta = 0.5f;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getLocationOnScreen(mLocationOnScreen);
    }

    /**
     * Set popup content view
     * @param contentView - content view
     * @param parentView - the parent view of popup window
     */
    public void setPopUpView(View contentView, View parentView){
        if (contentView != null && parentView != null) {
            mBubble = new PopupWindow(getContext());
            mBubble.setContentView(contentView);
            mBubble.setBackgroundDrawable(null);
            parent = parentView;
            setProgress(0);
        }
    }

    /**
     * custom user offset by X
     * @param userOffsetX - offset
     */
    public void setUserOffsetX(int userOffsetX) {
        this.userOffsetX = userOffsetX;
    }

    /**
     * custom user offset by Y
     * @param userOffsetY - offset
     */
    public void setUserOffsetY(int userOffsetY) {
        this.userOffsetY = userOffsetY;
    }

    /**
     * show popup window
     */
    public void show(){
        try {
            mBubble.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
        } catch (NullPointerException ex){
            Log.w(TAG, "PopUp window has no content view.\n Use setPopUpView(View, View) first for set content view");
        }
    }

    /**
     * hide popup window
     */
    public void hide(){
        try{
            mBubble.dismiss();
        } catch (NullPointerException ex) {
            Log.w(TAG, "PopUp window has no content view.\n Use setPopUpView(View, View) first for set content view");
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        if (mBubble != null && mBubble.isShowing()) {
            int val = (getProgress() * (getWidth() - 2 * getThumbOffset())) / getMax();
            mBubble.update(
                    (int) (getLeft() - userOffsetX + val + getThumbOffset() - mBubble.getContentView().getWidth() * mGravityDelta),
                    mLocationOnScreen[1] - getHeight() - userOffsetY,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
        }

        super.onDraw(canvas);
    }
}
