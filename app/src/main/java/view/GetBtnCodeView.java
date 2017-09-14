package view;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 获取验证码倒计时，按钮封装
 * 作者：Admin on 2017/4/21
 * 邮箱：tongzhenggang@126.com
 */
public class GetBtnCodeView extends CountDownTimer {
    private TextView mButton;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public GetBtnCodeView(TextView button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mButton.setClickable(false); //设置不可点击
        mButton.setText(millisUntilFinished / 1000 + "秒");  //设置倒计时时间
    }

    @Override
    public void onFinish() {
        mButton.setText("获取验证码");
        mButton.setClickable(true);//重新获得点击
    }
}
