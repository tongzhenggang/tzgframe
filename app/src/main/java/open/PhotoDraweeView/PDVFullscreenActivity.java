package open.PhotoDraweeView;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.frame.tzg.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * 大图展示图片，支持缩放操作
 */
public class PDVFullscreenActivity extends Activity {


    @BindView(R.id.PhotoDraweeView_Max)
    PhotoDraweeView PhotoDraweeViewMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_pdvfullscreen);
        ButterKnife.bind(this);

        /**
         * MAX PIC
         */
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
//        controller.setUri("http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg");
        String url = getIntent().getStringExtra("url");
        controller.setUri(url);
        controller.setOldController(PhotoDraweeViewMax.getController());
        PhotoDraweeViewMax.setMaximumScale(2);
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || PhotoDraweeViewMax == null) {
                    return;
                }
                PhotoDraweeViewMax.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        PhotoDraweeViewMax.setController(controller.build());

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //这里重写返回键
            finish();
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            return true;
        }
        return false;
    }

}
