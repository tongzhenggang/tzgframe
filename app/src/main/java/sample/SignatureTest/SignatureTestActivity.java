package sample.SignatureTest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.frame.tzg.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tool.ToastUtil;
import view.LinePathView;

public class SignatureTestActivity extends AppCompatActivity {


    @BindView(R.id.lpv_test)
    LinePathView lpvTest;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.activity_signature_test)
    LinearLayout activitySignatureTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_test);
        ButterKnife.bind(this);

//        修改背景、笔宽、颜色
        lpvTest.setBackColor(Color.BLACK);
        lpvTest.setPaintWidth(14);
        lpvTest.setPenColor(Color.WHITE);
        lpvTest.clear();

    }

    @OnClick({R.id.btn_clear, R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear://清除
                lpvTest.clear();//清除
                break;

            case R.id.btn_save://保存签名为图片

                if (lpvTest.getTouched()) {
                    try {
                        lpvTest.save("/sdcard/qm.png", true, 10);
                        setResult(100);


                        ToastUtil.show(getApplicationContext(), "保存成功，位置/sdcard/qm.png");

                        finish();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.show(getApplicationContext(), "您没有签名~");
                }

                break;
        }
    }

}
