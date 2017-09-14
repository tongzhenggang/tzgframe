package dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Admin on 2017/2/20 16:34
 * 邮箱：tongzhenggang@126.com
 */

@Entity
public class PhotoText{
    private String id;//  唯一ID String id=  UUID.randomUUID().toString();
    private int type;//0 文字模式  1 图片模式
    private String content;//本地存储内容，可以是图片和文字
    private int status;//离线上传状态 0 未上传 1 已上传
    private String proid;//关联项目ID
    private String time;//产生时间
    private String username;//用户
    @Generated(hash = 1853617065)
    public PhotoText(String id, int type, String content, int status, String proid,
            String time, String username) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.status = status;
        this.proid = proid;
        this.time = time;
        this.username = username;
    }
    @Generated(hash = 15253402)
    public PhotoText() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getProid() {
        return this.proid;
    }
    public void setProid(String proid) {
        this.proid = proid;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


}
