package open.tbs;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;



/**
 * 自定义的Android代码和JavaScript代码之间的桥梁类
 *
 * @author tongzhenggang
 * @title WebCoreJsInterface.java
 * @e-mail tongzhenggang@126.com
 */
public class WebCoreJsInterface {
    private String str;
    private String mAppInfoFilePath, sdk, version, model, DeviceId;

    private static Context mContext;

    public WebCoreJsInterface(Context c) {
        mContext = c;
    }

    // 如果target 大于等于API 17，则需要加上如下注解
    // @JavascriptInterface
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();

    }

    /*
     * 关闭当前activity
     */
    @JavascriptInterface
    public void finish(String toast) {
        ((Activity) mContext).finish();
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    /*
     * 关闭APP
     */
    @JavascriptInterface
    public void Close() {
        new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle("温馨提示")
                .setMessage("确定要退出么？")
                .setCancelable(true)
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        MainActivity.activity.finish();
                        ((Activity) mContext).finish();

//                        mContext.startActivity(new Intent(mContext, LoginActivity.class));//登陆
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    @JavascriptInterface
    public String getDevice() {

        // 获取设备信息
        // sdk = android.os.Build.VERSION.SDK; // SDK号
        // version = android.os.Build.VERSION.RELEASE; // android系统版本号
        // model = android.os.Build.MODEL; // 手机型号
        // model = model.replace(" ", "").toLowerCase();// 小写转换去空格
        TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        DeviceId = manager.getDeviceId().trim();// 设备编号

        str = DeviceId;
        return str;
    }

    /**
     * 客户端展示对话框
     */
    @JavascriptInterface
    public static void Dialogshow(String Msg) {
        new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("提示").setMessage(Msg)
                .setCancelable(true).setPositiveButton("朕知道了", null).show();
    }


    /**
     * 播放音频
     */
    @JavascriptInterface
    public static void Mp3play(String type, String url) {
        //调用系统播放器
        if (type.equals("0")) {
            Intent it = new Intent(Intent.ACTION_VIEW);
            it.setDataAndType(Uri.parse(url), "audio/MP3");
            ((Activity) mContext).startActivity(it);
        }
        //不调用播放器
        else {
            MediaPlayer mediaPlayer = new MediaPlayer();
            Uri uri = Uri.parse(url);
            mediaPlayer = MediaPlayer.create(mContext, uri);
            mediaPlayer.start();
        }
    }

    /**
     * 再次打开WebCoreActivity
     *
     * @param title
     * @param Url
     */
    @JavascriptInterface
    public static void OpenWeb(String title, String Url) {
        // ToastUtil.show(mContext, "title："+title+"Url:"+Url);

        //PUB.web(mContext, title, Url);

    }

    /*
    * Explain：更改密码原生界面
    * Create by TongZhenggang@126.com
    */
    @JavascriptInterface
    public void AlterPasswordActivity() {
//        mContext.startActivity(new Intent(mContext, AlterPasswordActivity.class));
    }

    /*
    * Explain：设置头像原生界面
    * Create by TongZhenggang@126.com
    */
    @JavascriptInterface
    public void IconActivity() {
//        mContext.startActivity(new Intent(mContext, SetHeadActivity.class));
    }


    /**
     * 客户端提示，关闭网页并返回原生界面
     * EXP:<a href=javascript:APP.DialogFinish('测试')>test<a/>
     */
    @JavascriptInterface
    public static void DialogFinish(String Msg) {
        new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle("提示")
                .setMessage(Msg)
                .setCancelable(true)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Activity) mContext).finish();
                            }
                        })
                .show();
    }

    /**
     * 回到首页
     * EXP:<a href=javascript:APP.Mainactivity()>test<a/>
     */
    @JavascriptInterface
    public static void Mainactivity() {
//        MainActivity.activity.finish();
        ((Activity) mContext).finish();

//        mContext.startActivity(new Intent(mContext, MainActivity.class));

    }
}