package view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dykj.deshang.R;
import com.dykj.deshang.fragment1.ProductFilterAdapter1;
import com.dykj.deshang.fragment1.ProductFilterAdapter2;

import org.greenrobot.eventbus.EventBus;

import dao.apidao.ProClassBean;
import sample.MsgEvent;

/**
 * Created by sj on 2017/8/9.
 */

public class ProductFilterDialog extends Dialog {

    private NoScrollGridView ngv1;
    private NoScrollGridView ngv2;
    private TextView title;
    private TextView clear;
    private TextView sureTV;
    private ProductFilterAdapter2 adapter2;
    private ProductFilterAdapter1 mAdapter;
    private int cat=0;
    public ProductFilterDialog(Context context) {
        super(context);
        initView(context);
    }

    protected    ProductFilterDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    public ProductFilterDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public void initView(final Context context) {
        View view = View.inflate(context, R.layout.product_dialog_layout, null);
        ngv1 = (NoScrollGridView) view.findViewById(R.id.product_ngv1);
        ngv2 = (NoScrollGridView) view.findViewById(R.id.product_ngv2);
        title = (TextView) view.findViewById(R.id.product_tv_title);
        clear = (TextView) view.findViewById(R.id.product_clear);
        sureTV = (TextView) view.findViewById(R.id.product_sure);
        addContentView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        getWindow().setGravity(Gravity.RIGHT);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels * 2 / 3;
        lp.height = context.getResources().getDisplayMetrics().heightPixels;
        getWindow().setAttributes(lp);
        initData(context);
        ngv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setPosition(position);
                title.setText(mAdapter.getItem(position).getCatName());
                adapter2.setList(mAdapter.getItem(position).getSmalldata());

            }
        });

        ngv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter2.setPosition(position);
                cat = adapter2.getItem(position).getCatId();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MsgEvent(0));
                dismiss();
            }
        });
        sureTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new MsgEvent(cat));
                dismiss();
            }
        });
    }
    public void initData(Context context){
        mAdapter = new ProductFilterAdapter1(context);
        mAdapter.setListener(new ProductFilterAdapter1.OnFinishListener() {
            @Override
            public void onFinish() {
                ProClassBean.DataBean dataBean = mAdapter.getItem(0);
                title.setText(dataBean.getCatName());
                adapter2 = new ProductFilterAdapter2(dataBean.getSmalldata());
                ngv2.setAdapter(adapter2);
            }
        });
        ngv1.setAdapter(mAdapter);

    }
}
