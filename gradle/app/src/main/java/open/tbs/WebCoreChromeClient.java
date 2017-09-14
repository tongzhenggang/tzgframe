package open.tbs;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * 作者：Admin on 2017/2/10 09:50
 * 邮箱：tongzhenggang@126.com
 */
public class WebCoreChromeClient extends WebChromeClient {
    Activity mActivity;
    ProgressBar mPbBar;
    String mTitle;

    public WebCoreChromeClient(Activity activity, ProgressBar pbBar, String title) {
        super();

        this.mActivity = activity;
        this.mPbBar = pbBar;
        this.mTitle = title;


    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mTitle.equals("")) {
            mActivity.setTitle(title);
        } else {
            mActivity.setTitle(mTitle);
        }

    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        WebCoreJsInterface.Dialogshow(message);
        result.confirm();

        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        // TODO Auto-generated method stub
        mPbBar.setProgress(newProgress);

        if (mPbBar != null && newProgress != 100) {
            mPbBar.setVisibility(View.VISIBLE);
        } else {
            mPbBar.setVisibility(View.GONE);
        }


    }
}
