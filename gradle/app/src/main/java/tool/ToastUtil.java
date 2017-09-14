package tool;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.tzg.R;

public class ToastUtil {

    public static void show(Context context, String msg) {

//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_util, null);
        TextView title = (TextView) layout.findViewById(R.id.tv_toast);
        title.setText(msg);
//        ImageView img = (ImageView) layout.findViewById(R.id.define_img);
//        img.setImageResource(R.drawable.wirelessqa);
//        TextView text = (TextView) layout.findViewById(R.id.define_text);
//        text.setText("www.wirelessqa.com");
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
// 替换掉原有的ToastView
        toast.setView(layout);
        toast.show();
    }

}