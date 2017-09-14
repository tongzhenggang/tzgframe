package tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 */
public class PUB {
    /** 获取SDK版本 **/
    public static final Integer version = Integer.valueOf(android.os.Build.VERSION.SDK);

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    /*
     * 是否连接WIFI
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    /*
     * 考试弹出框进度 GridView 高度自适应
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 9;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight + 50;
        // 设置margin
        ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    /*
     * 百分比计算
     */
    public static String getPercent(int x, int total) {
        String result = "";// 接受百分比的值
        double x_double = x * 1.0;
        double total_double = total * 1.0;
        double tempresult = x / total_double;
        NumberFormat nf = NumberFormat.getPercentInstance(); // 注释掉的也是一种方法
        nf.setMinimumFractionDigits(2); // 保留到小数点后几位
        // DecimalFormat df1 = new DecimalFormat("0.00%"); //##.00%
        // 百分比格式，后面不足2位的用0补齐
        result = nf.format(tempresult);
        // result= df1.format(tempresult);
        return result;
    }

    /**
     * 监测网络是否畅通
     *
     * @param context
     * @return
     */
    public static Boolean checkNetwork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        // 检查网络连接，如果无网络可用，就不需要进行连网操作等
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !manager.getBackgroundDataSetting()) {
            return false;
        }
        return true;
    }

    /**
     * BITMAP转base64编码
     *
     * @param bitmap
     * @return
     */
    public static String bitmap2String(Bitmap bitmap) {

        // 将Bitmap转换成字符串
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        String string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;

    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyyMMdd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * ArrayList<String> 转 String[]
     **/
    public static String[] trans(ArrayList<String> als) {
        String[] sa = new String[als.size()];
        als.toArray(sa);
        return sa;
    }

    /**
     * String[] 转 ArrayList<String>
     **/
    public static ArrayList<String> trans(String[] sa) {
        ArrayList<String> als = new ArrayList<String>(0);
        for (int i = 0; i < sa.length; i++) {
            als.add(sa[i]);
        }
        return als;
    }

    /**
     * 实现对状态栏的控制,是否全屏
     *
     * @param context
     * @param enable
     */
    public static void TopStatusFull(Context context, boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            ((Activity) context).getWindow().setAttributes(lp);
            ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = ((Activity) context).getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ((Activity) context).getWindow().setAttributes(attr);
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 设置网络对话框
     *
     * @param context
     */
    public static void SetNetDialogshow(final Context context) {
        try {
            new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).setTitle("没有网络(●-●) ")
                    .setMessage("世界上最遥远的距离就是没网。检查设置").setCancelable(false)
                    .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("忽略", null).show();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * SharedPreferences 存储与读取 value.length()>1 写入，value="#read"则读取
     *
     * @param ct
     * @param name
     * @param value
     * @return
     */
    public static String SharedPreferences(Context ct, String name, String value) {
        SharedPreferences sp = ct.getSharedPreferences(ct.getPackageName() + "_SP", Activity.MODE_PRIVATE);

        if (!value.equals("#read")) {
            Logger.i("name:" + name + "\nvalue:" + value);
            // 写入
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(name, value);
            editor.commit();
        } else {
            // 读取
            value = sp.getString(name, "");
        }

        return value;
    }


    /*
     * 获取url文件名
     */
    public static String GetUrlFilename(String Url) {
        String result = "";
        try {
            result = Url.substring(Url.lastIndexOf('/') + 1);
        } catch (Exception ex) {
            result = "";
        }

        return result;
    }

    private static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }


    /*
     * WPS打开文件
     */
    public static boolean openFile(Context mContext, String path) {
//		Intent intent = new Intent();
//		Bundle bundle = new Bundle();
//		 bundle.putString("OpenMode", "ReadMode");
//		// bundle.putBoolean(SEND_CLOSE_BROAD, true);
//		// bundle.putString(THIRD_PACKAGE, selfPackageName);
//		// bundle.putBoolean(CLEAR_BUFFER, true);
//		// bundle.putBoolean(CLEAR_TRACE, true);
//		// bundle.putBoolean(CLEAR_FILE, true);
//		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setAction(android.content.Intent.ACTION_VIEW);
//		// String className =
//		// "cn.wps.moffice.documentmanager.PreStartActivity2";
//		// String packageName = "cn.wps.moffice_eng";
//		// intent.setClassName(packageName, className);
//		File file = new File(path);
//		if (file == null || !file.exists()) {
//			return false;
//		}
//		String type = getMIMEType(file);
//		// 设置intent的data和Type属性。
//		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
//		// 跳转
//
//		// Uri uri = Uri.fromFile(file);
//		// intent.setData(uri);
//		intent.putExtras(bundle);
//
//		try {
//			mContext.startActivity(intent);
//		} catch (ActivityNotFoundException e) {
//			e.printStackTrace();
//			return false;
//		}

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("OpenMode", "ReadOnly");
        bundle.putBoolean("SendCloseBroad", true);
        bundle.putString("ThridPackage", "com.dykj.huaxin");
        bundle.putBoolean("ClearBuffer", true);
        bundle.putBoolean("ClearTrace", true);
        bundle.putBoolean("ClearFile", true);
//		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String className = "cn.wps.moffice.documentmanager.PreStartActivity2";
        String packageName = "cn.wps.moffice_eng";
        intent.setClassName(packageName, className);

        File file = new File(path);
        if (file == null || !file.exists()) {
            return false;
        }

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        intent.putExtras(bundle);

        try {
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            {".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"}, {".avi", "video/x-msvideo"}, {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"}, {".c", "text/plain"}, {".class", "application/octet-stream"},
            {".conf", "text/plain"}, {".cpp", "text/plain"}, {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"}, {".gif", "image/gif"}, {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"}, {".h", "text/plain"}, {".htm", "text/html"}, {".html", "text/html"},
            {".jar", "application/java-archive"}, {".java", "text/plain"}, {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"}, {".js", "application/x-javascript"}, {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"}, {".m4a", "audio/mp4a-latm"}, {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"}, {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"}, {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"}, {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"}, {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"}, {".pdf", "application/pdf"},
            {".png", "image/png"}, {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"}, {".rc", "text/plain"}, {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"}, {".sh", "text/plain"}, {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"}, {".txt", "text/plain"}, {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"}, {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"}, {".z", "application/x-compress"}, {".zip", "application/x-zip-compressed"},
            {"", "*/*"}};

    /*
     * 7以内阿拉伯数字转中文数字，用于日历星期汉字转换
     */
    public static String Num2Cn(int i) {
        String[] str = {"一", "二", "三", "四", "五", "六", "日"};

        String mStr = "" + i;
        try {
            mStr = str[i - 1].toString();
        } catch (Exception ex) {
            mStr = "日";
        }
        return mStr;
    }

    /*
     * 转化01,02 为1,2形式
     */
    public static String Date2Mdate(String date) {
        date = date.replace("-01", "-1");
        date = date.replace("-02", "-2");
        date = date.replace("-03", "-3");
        date = date.replace("-04", "-4");
        date = date.replace("-05", "-5");
        date = date.replace("-06", "-6");
        date = date.replace("-07", "-7");
        date = date.replace("-08", "-8");
        date = date.replace("-09", "-9");

        return date;
    }

    /*
     * ProgressDialog 是否显示
     */
    public static ProgressDialog ProgressDialog(ProgressDialog pdialog) {
        // waitdialog.setTitle("提示");
        pdialog.setMessage("玩命加载中...");
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(true);
        pdialog.show();

        return pdialog;
    }

    /**
     * 判断SD卡是否存在，存在则返回绝对路径
     *
     * @return
     */
    public static File GetSDPath() {
        File sdDir = null;
        // 判断SD卡 是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            // 获取根目录
            sdDir = Environment.getExternalStorageDirectory();
        } else {
            sdDir = null;
        }
        return sdDir;

    }

    /**
     * 判断SD卡剩余空间
     *
     * @return
     */
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        // return freeBlocks * blockSize; //单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 判断SD卡大小
     *
     * @return
     */
    public static long getSDAllSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 获取所有数据块数
        long allBlocks = sf.getBlockCount();
        // 返回SD卡大小
        // return allBlocks * blockSize; //单位Byte
        // return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取文件大小:获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取文件大小:获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            Logger.e("size:" + size);
        } else {
            file.createNewFile();
            Logger.e("获取文件大小:文件不存在!");
        }
        Logger.e("size:" + size);
        return size;
    }


    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    public static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 根据URL获取URLConnection对象
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static URLConnection GetUrlFileSize(String url) throws IOException {
        URL myURL = new URL(url);
        URLConnection conn = myURL.openConnection();
        conn.setRequestProperty("Accept-Encoding", "identity");
        conn.connect();
        return conn;

    }

    /**
     * 密码MD5加密
     *
     * @param password
     */
    public static String string2md5Ppwd(String password) {
        return MD5Util.string2MD5("TPSHOP" + password);
    }

    /**
     * 距离转换人性化数据
     */
    public static String juli2readjuli(int juli) {
        String sJuli = "";

        if (juli > 999) {
            int num = juli / 1000;//km
            sJuli = "距离您大约" + num + "公里";
        } else if (juli > 99999) {
            int num = juli / 100000;//100km
            sJuli = "距离您大约" + num + "百公里";
        } else if (juli < 10) {
            sJuli = "就在您附近";
        } else {
            sJuli = "距离您大约" + juli + "米";
        }


        return sJuli;
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isMapAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public static double pi = 3.141592653589793 * 3000.0 / 180.0;

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param gg_lat
     * @param gg_lon
     * @return
     */
    public static Gps gcj02_To_Bd09(double gg_lon, double gg_lat) {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new Gps(bd_lon, bd_lat);
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法   将 BD-09 坐标转换成GCJ-02 坐标
     *
     * @param bd_lon
     * @param bd_lat
     * @return
     */
    public static Gps bd09_To_Gcj02(double bd_lon, double bd_lat) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new Gps(gg_lon, gg_lat);
    }

    //Gps类
    public static class Gps {

        public double lat;
        public double lon;

        public Gps(double lon, double lat) {
            this.lat = lat;
            this.lon = lon;
        }

        public void print() {
            System.out.println(this.lon + "," + this.lat);
        }
    }


    /**
     * 保存View为图片的方法
     */
    public static  void saveBitmap(View v, String name) {


        String fileName = name + ".png";


        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        String TAG = "TIKTOK";
        Log.e(TAG, "保存图片");
        File f = new File("/sdcard/", fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
