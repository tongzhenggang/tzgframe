package open.tbs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * 作者：Admin on 2017/2/10 09:44
 * 邮箱：tongzhenggang@126.com
 */
public class WebCoreClient extends WebViewClient {

    Activity mActivity;
    com.tencent.smtt.sdk.WebView  mTbsContent;

    public WebCoreClient(Activity activity, com.tencent.smtt.sdk.WebView  tbsContent) {
        this.mActivity=activity;
        this.mTbsContent = tbsContent;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        Intent intent;
        // 判断URL
        if (url.startsWith("http:") || url.startsWith("https:")) {
            view.loadUrl(url);
        }
        // 调用拨号程序
        if (url.startsWith("tel:")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mActivity.startActivity(intent);
        }
        // 发送短信
        if (url.startsWith("sms:")) {
            Uri uri = Uri.parse(url);
            intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", "");
            mActivity.startActivity(intent);
        }
        // 返回
        if (url.startsWith("go:")) {
            if (url.startsWith("go:back")) {
                if (mTbsContent.canGoBack()) {
                    mTbsContent.stopLoading();
                    mTbsContent.goBack();
                } else {//go:finish
                    mActivity.finish();
                }
            }
        }

        return true;
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        mTbsContent.stopLoading();
        mTbsContent.clearView();

        mTbsContent.setVisibility(View.GONE);
        Log.e("error", "errorCode:" + errorCode + "  description:" + description + "  failingUrl:" + failingUrl);

        int Theme = android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
        int version = Build.VERSION.SDK_INT;
        if (version > 21) {
            Theme = android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
        } else {
            Theme = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, Theme);
        builder.setTitle("温馨提示");
        String msg = "";//"检测到状态异常，请联系开发者"+description + "[" + errorCode + "]";
        //if(errorCode==-2){
        msg = "状态异常，请联系管理员或检查网络后再重试！";
        //}
        builder.setMessage(msg);

        builder.setCancelable(false);
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mActivity.finish();
            }
        });
        builder.show();

    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        // TODO Auto-generated method stub

        Log.d("should", "request.getUrl().toString() is " + request.getUrl().toString());

        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

    }
}
