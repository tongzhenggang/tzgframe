package sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.frame.tzg.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import open.tbs.WebCoreAction;
import open.zxing.android.ZxingCaptureActivity;
import tool.ToastUtil;

/**
 * 测试方法类，提供测试研究使用
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_toast)
    Button tvToast;
    @Bind(R.id.content_test)
    RelativeLayout contentTest;
    @Bind(R.id.btn_tbs)
    Button btnTbs;
    @Bind(R.id.btn_multiImageSelector)
    Button btnMultiImageSelector;
    @Bind(R.id.btn_zxing)
    Button btnZxing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//开启返回按钮

        tvToast.setOnClickListener(this);
        btnTbs.setOnClickListener(this);
        btnMultiImageSelector.setOnClickListener(this);
        btnZxing.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_toast:
                ToastUtil.show(getApplicationContext(), "自定义Toast显示");
                break;
            case R.id.btn_tbs://腾讯X5

//                new WebCoreAction(this, "http://qq.com");//自动获取标题
                new WebCoreAction(this, "http://qq.com", "测试标题");//手动设置标题

                break;
            case R.id.btn_multiImageSelector://图片选择
                MultiImageSelector.create(this)
                        .showCamera(true) // 是否显示相机. 默认为显示
                        .count(1) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .single() // 单选模式
//                        .multi() // 多选模式, 默认模式;
//                        .origin(null) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(this, 0);

                break;
            case R.id.btn_zxing://二维码扫描
                startActivity(new Intent(this, ZxingCaptureActivity.class));

                break;

            default:
                ToastUtil.show(getApplicationContext(), "default");
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home://返回按钮操作
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
