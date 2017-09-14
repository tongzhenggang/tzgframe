package view;

import android.content.Context;
import android.widget.TextView;

import tool.ToastUtil;


/**
 * 获取手机验证码
 * 作者：Admin on 2017/4/21
 * 邮箱：tongzhenggang@126.com
 */
public class GetMobileCode {

    Context mContext;

    /**
     * 获取手机验证码
     *
     * @param mobile
     * @param btnCode
     */
    public GetMobileCode(Context context, String mobile, TextView btnCode) {

        this.mContext = context;

        if (mobile.length() != 11) {
            ToastUtil.show(mContext, "手机号码位数不正确");
        } else {
            //按钮倒计时
            GetBtnCodeView mGetBtnCodeView = new GetBtnCodeView(btnCode, 120000, 1000);
            mGetBtnCodeView.start();


        }
    }
}
