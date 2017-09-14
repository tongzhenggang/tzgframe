package sample.SwipeMenuListView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.frame.tzg.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import tool.ToastUtil;

/**
 * ListView滑动按钮空间实现，示例
 */
public class SmlistTestActivity extends AppCompatActivity {


    @BindView(R.id.splistView)
    SwipeMenuListView splistView;
    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout storeHousePtrFrame;
    @BindView(R.id.activity_smlist_test)
    RelativeLayout activitySmlistTest;
    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smlist_test);
        ButterKnife.bind(this);


        //        设置PtrHandler  PtrHandler可以自定义 需要实现PtrHandler
        storeHousePtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                ToastUtil.show(getApplicationContext(), "下拉刷新");

                storeHousePtrFrame.refreshComplete();

            }
        });


// set creator
        splistView.setMenuCreator(creator);
        // Right
//        splistView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        // Left
        splistView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


        //测试的数据。
        data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add("更新的数据：" + i);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        splistView.setAdapter(adapter);


        splistView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        ToastUtil.show(getApplicationContext(), "open");
                        break;

                    case 1:
                        // delete
                        ToastUtil.show(getApplicationContext(), "delete");

                        data.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }


    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // create "open" item
            SwipeMenuItem openItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            openItem.setBackground(R.color.colorPrimary);
            // set item width
            openItem.setWidth(dp2px(80));
            // set item title
            openItem.setTitle("详情");
            // set a icon
//            openItem.setIcon(android.R.drawable.ic_btn_speak_now);
            // set item title fontsize
            openItem.setTitleSize(18);
            // set item title font color
            openItem.setTitleColor(Color.WHITE);
            // add to menu
            menu.addMenuItem(openItem);

//            // create "delete" item
//            SwipeMenuItem deleteItem = new SwipeMenuItem(
//                    getApplicationContext());
//            // set item background
//            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                    0x3F, 0x25)));
//            // set item width
//            deleteItem.setWidth(dp2px(90));
//            // set a icon
//            deleteItem.setIcon(android.R.drawable.ic_delete);
//            // add to menu
//            menu.addMenuItem(deleteItem);
        }
    };


    public int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


}
