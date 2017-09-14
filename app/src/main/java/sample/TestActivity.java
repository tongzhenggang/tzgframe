package sample;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alipay.sdk.app.PayTask;
import com.dd.processbutton.iml.ActionProcessButton;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.frame.tzg.MainFragmentActivity;
import com.frame.tzg.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.suke.widget.SwitchButton;
import com.youth.banner.Banner;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import config.MyApplication;
import config.Urls;
import dao.greendao.TestDao;
import es.dmoral.toasty.Toasty;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import kr.co.namee.permissiongen.PermissionGen;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.relex.photodraweeview.PhotoDraweeView;
import okhttp3.Response;
import open.PhotoDraweeView.PDVFullscreenActivity;
import open.tbs.WebCoreAction;
import open.zxing.android.ZxingCaptureActivity;
import sample.SignatureTest.SignatureTestActivity;
import sample.SwipeMenuListView.SmlistTestActivity;
import tool.ToastUtil;

/**
 * 测试方法类，提供测试研究使用
 */
public class TestActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.btn_alipay)
    Button btnAlipay;
    @BindView(R.id.btn_PhotoDraweeView)
    Button btnPhotoDraweeView;
    @BindView(R.id.btn_amap)
    Button btnAmap;
    @BindView(R.id.sdv_img)
    PhotoDraweeView sdvImg;
    @BindView(R.id.tv_toast)
    Button tvToast;
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.btn_tbs)
    Button btnTbs;
    @BindView(R.id.btn_multiImageSelector)
    Button btnMultiImageSelector;
    @BindView(R.id.btn_zxing)
    Button btnZxing;
    @BindView(R.id.btnSignIn)
    ActionProcessButton btnSignIn;
    @BindView(R.id.btn_dao)
    Button btnDao;
    @BindView(R.id.NumberProgressBar)
    com.daimajia.numberprogressbar.NumberProgressBar NumberProgressBar;
    @BindView(R.id.btn_SwipeMenuListView)
    Button btnSwipeMenuListView;
    @BindView(R.id.btn_signature)
    Button btnSignature;
    @BindView(R.id.btn_okgopost)
    Button btnOkgopost;
    @BindView(R.id.btn_okgoget)
    Button btnOkgoget;
    @BindView(R.id.btn_tab)
    Button btnTab;
    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout storeHousePtrFrame;
    @BindView(R.id.content_test)
    RelativeLayout contentTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//开启返回按钮

        Init();


    }

    private void Init() {


        /**
         * android 6.0 申请权限
         */

        PermissionGen.needPermission(this, 100,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                }
        );


//        设置PtrHandler  PtrHandler可以自定义 需要实现PtrHandler
        storeHousePtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                storeHousePtrFrame.refreshComplete();
            }
        });

        //进度
        NumberProgressBar.setMax(100);
        NumberProgressBar.setProgress(66);


        /**
         * https://github.com/ongakuer/PhotoDraweeView
         */
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri("http://img2.3lian.com/2014/f2/37/d/40.jpg");
        controller.setOldController(sdvImg.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || sdvImg == null) {
                    return;
                }
                sdvImg.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        sdvImg.setController(controller.build());

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            Toast.makeText(TestActivity.this, result,
                    Toast.LENGTH_LONG).show();
        }

        ;
    };


    @OnClick({R.id.tv_toast, R.id.btn_tbs, R.id.btn_multiImageSelector
            , R.id.btn_zxing, R.id.btnSignIn, R.id.btn_dao, R.id.btn_SwipeMenuListView
            , R.id.btn_signature, R.id.btn_okgopost, R.id.btn_okgoget, R.id.btn_tab
            , R.id.btn_amap, R.id.btn_PhotoDraweeView, R.id.btn_alipay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alipay:


                final String orderInfo = "test-订单信息";   // 订单信息

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(TestActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);//alipay.payV2(orderInfo,true);

                        Message msg = new Message();
//                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
            case R.id.btn_PhotoDraweeView://PhotoDraweeView
                Intent mIntent = new Intent(this, PDVFullscreenActivity.class);
                mIntent.putExtra("url", "http://bbsfiles.vivo.com.cn/vivobbs/attachment/forum/201601/11/183813inr58azs5hnp30de.jpg");
                startActivity(mIntent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);

                break;

            case R.id.tv_toast:
                ToastUtil.show(getApplicationContext(), "自定义Toast显示");
                break;
            case R.id.btn_tbs://腾讯X5


                new WebCoreAction(this, "http://study.cp.hxdi.cn/upload/WORD.DOCX");

//                new WebCoreAction(this, "http://qq.com");//自动获取标题
//                new WebCoreAction(this, "http://qq.com", "测试标题");//手动设置标题

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
            case R.id.btnSignIn://进度按钮
//                EventBus.getDefault().post(new MessageEvent("数据回传6666666"));

                btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);

// no progress
                btnSignIn.setProgress(0);
// progressDrawable cover 50% of button width, progressText is shown
                btnSignIn.setProgress(50);
// progressDrawable cover 75% of button width, progressText is shown
                btnSignIn.setProgress(75);
// completeColor & completeText is shown
                btnSignIn.setProgress(100);

// you can display endless google like progress indicator
                btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
// set progress > 0 to start progress indicator animation
                btnSignIn.setProgress(1);


                //-------MaterialDialog示例
                new MaterialDialog.Builder(this)
                        .title("标题")
                        .content("内容")
                        .positiveText("确定")
                        .negativeText("取消")

                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ToastUtil.show(getApplicationContext(), "确定");
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ToastUtil.show(getApplicationContext(), "取消");
                            }
                        })

                        .show();

                break;
            case R.id.btn_dao://greenDAO

                final TestDao mTestDao = MyApplication.getInstances().getDaoSession().getTestDao();
/*
                mTestDao.deleteAll();//清除数据

                *//**
             * 批量保存数据
             */
//                OkGo.get("http://jianshi.zjr1.com/mapi/jianshiapi.asmx/GetChannelList")     // 请求方式和请求url
//                        .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//                        .cacheKey("cacheKey-" + getApplicationContext().getClass().getName())            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                        .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                // s 即为所需要的结果
//                                ToastUtil.show(getApplicationContext(), s);
//
//                                JSONArray jsonArray = null;
//                                try {
//                                    jsonArray = new JSONArray(new JSONObject(s).get("data").toString());
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                                ToastUtil.show(getApplicationContext(), jsonArray.toString());
//                                ArrayList<Test> dataBeans = new ArrayList();
//                                dataBeans = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Test>>() {
//                                }.getType());
//
//                                Test mResult = new Test();
//                                for (int i = 0; i < dataBeans.size(); i++) {
//                                    mResult = dataBeans.get(i);
//
//                                    Logger.d(mResult.toString());
//
//                                    mTestDao.insert(mResult);//添加一个
//                                }
//
//
//                                //查
//                                List<Test> mList = mTestDao.loadAll();
//                                String str = "\r\n";
//                                for (int i = 0; i < mList.size(); i++) {
//                                    str += "【1】" + mList.get(i).getSysMenuImage() + "【2】" + mList.get(i).getSysMenuStr()
//                                            + "【3】" + mList.get(i).getSysMenuNum() + "【4】" + mList.get(i).getSysMenuName()
//                                            + "\r\n";
//                                }
//                                btnDao.setText("查询全部数据==>\r\n" + str);
//                                Logger.d(str);
//
//                            }
//                        });


                OkGo.post("url")
                        .params("param1", "paramValue1")
                        .params("param2", "paramValue2")
                        .execute(new Callback<Object>() {
                            @Override
                            public void onStart(Request<Object, ? extends Request> request) {

                            }

                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<Object> response) {

                            }

                            @Override
                            public void onCacheSuccess(com.lzy.okgo.model.Response<Object> response) {

                            }

                            @Override
                            public void onError(com.lzy.okgo.model.Response<Object> response) {

                            }

                            @Override
                            public void onFinish() {

                            }

                            @Override
                            public void uploadProgress(Progress progress) {

                            }

                            @Override
                            public void downloadProgress(Progress progress) {

                            }

                            @Override
                            public Object convertResponse(Response response) throws Throwable {
                                return null;
                            }
                        });




/*

                PhotoTextDao mPhotoDao = MyApplication.getInstances().getDaoSession().getPhotoTextDao();


//                //增
                String id=  UUID.randomUUID().toString();//随机ID
                //获取时间戳
                String time = String.valueOf(System.currentTimeMillis());

                PhotoText mPhotoText = new PhotoText(id,0,"/sdcard/test.jpg",0,"proid0001",time,"tzg");
                mPhotoDao.insert(mPhotoText);//添加一个

//                //删
//                mUserDao.deleteByKey((long)2);

//                //改
//                User mUser = new User((long) 2, "66666666666666888888888888888vvvsdfs撒大声地");
//                mUserDao.update(mUser);

                //查
                List<PhotoText> photos = mPhotoDao.loadAll();
                String str = "\r\n";
                for (int i = 0; i < photos.size(); i++) {
                    str += "【ID】"+photos.get(i).getId() + "【类型】"+photos.get(i).getType()
                            +  "【内容路径】" +photos.get(i).getContent() + "【状态】"+photos.get(i).getStatus()
                            +  "【项目ID】" +photos.get(i).getProid() + "【UserName】"+photos.get(i).getUsername()
                            +  "【内容路径】" +photos.get(i).getContent()
                            +"\r\n";
                }
                btnDao.setText("查询全部数据==>\r\n" +str);
                Logger.d(str);
*/

                break;

            case R.id.btn_SwipeMenuListView://ListView滑动删除

                startActivity(new Intent(getApplicationContext(), SmlistTestActivity.class));
                break;

            case R.id.btn_signature://签名保存图片

                startActivity(new Intent(getApplicationContext(), SignatureTestActivity.class));

                break;

            case R.id.btn_okgopost://OKGO POST请求
                ////////////////----OKGO简单的使用---//////////////

//                OkGo.post(Urls.URL_METHOD)
//                        .tag(this)
//                        .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
//                        .params("param1", "paramValue1")        // 这里可以上传参数
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(Response<Test> response) {
//
//                            }
//                        });



                break;

            case R.id.btn_okgoget://OKGO GET请求
                // https://github.com/jeasonlzy/okhttp-OkGo ////////

                OkGo.<String>get(Urls.Domain)
                        .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                        .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                        .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍                        .headers(HttpHeaders.HEAD_KEY_USER_AGENT, "abcd")//
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                Toasty.info(getApplicationContext(),"code:"+response.code()).show();
                                Toasty.info(getApplicationContext(),response.body().toString()).show();

                            }

                            @Override
                            public void onError(com.lzy.okgo.model.Response<String> response){
                                Toasty.info(getApplicationContext(),"response:"+response.code()+"  "+response.body()).show();
                            }

                        });

                break;
            case R.id.btn_tab://TAB MainFragmentActivity 框架
                startActivity(new Intent(getApplicationContext(), MainFragmentActivity.class));
                break;

            case R.id.btn_amap://高德地图

                break;


            ///////////////////////////////////////////////////////////////////////////////////////
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

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


}
