package open.tbs;

import android.content.Context;
import android.content.Intent;

/**
 * 执行webcoreactivity操作，数据传递
 * 作者：Admin on 2017/2/10 11:35
 * 邮箱：tongzhenggang@126.com
 */

public class WebCoreAction {

    /**
     * 只传递URL，页面标题读取html标签的title
     * @param context
     * @param inputUrl
     */
    public WebCoreAction(Context context, String inputUrl) {
        Intent intent= new Intent(context, WebCoreActivity.class);
        intent.putExtra("inputurl",inputUrl);
        intent.putExtra("title","");
        context.startActivity(intent);
    }

    /**
     * 传递URL，和页面标题
     * @param context
     * @param title
     * @param inputUrl
     */
    public WebCoreAction(Context context,String inputUrl, String title ) {
        Intent intent= new Intent(context, WebCoreActivity.class);
        intent.putExtra("inputurl",inputUrl);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

}
