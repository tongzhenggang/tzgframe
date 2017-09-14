package open.tbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.frame.tzg.R;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基于腾讯X5内核封装的webview公共访问模块
 * by tzg
 */
public class WebCoreActivity extends AppCompatActivity {


    @BindView(R.id.webview_toolbar)
    Toolbar webviewToolbar;
    @BindView(R.id.pb_bar)
    ProgressBar pbBar;
    @BindView(R.id.tbs_content)
    WebView tbsContent;
    @BindView(R.id.content_web_core)
    RelativeLayout contentWebCore;
    private String InputUrl = "http://m.baidu.com";
    private String Title = "";//默认为空时，则自动读取html页面title标签数据，一般为空即可
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_core);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.webview_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//开启返回按钮


        try {
            initReceive();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initSet();
    }

    /**
     * 接收Intent参数
     */
    private void initReceive() throws Exception {

        try {
            this.InputUrl = getIntent().getStringExtra("inputurl");
        } catch (Exception ex) {
            Logger.e(ex.toString());
        }
        try {
            this.Title = getIntent().getStringExtra("title");
        } catch (Exception ex) {
            Logger.e(ex.toString());
        }
    }

    private void initSet() {

        pbBar.setMax(100);
        pbBar.setProgressDrawable(this.getResources().getDrawable(R.drawable.color_progressbar));

        tbsContent.loadUrl(InputUrl);
        WebSettings webSettings = tbsContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        优先使用缓存，WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        不使用缓存，WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        tbsContent.addJavascriptInterface(new WebCoreJsInterface(this), "APP");
        tbsContent.setWebViewClient(new WebCoreClient(this, tbsContent));
        tbsContent.setWebChromeClient(new WebCoreChromeClient(this, pbBar, Title));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home://返回按钮操作
                if (tbsContent.canGoBack()) {
                    tbsContent.goBack();// 返回前一个页面
                } else {
                    finish();
                }
                break;

            case R.id.exit://关闭
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && tbsContent.canGoBack()) {
            tbsContent.goBack();// 返回前一个页面
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_webcore_menu, menu);
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
