package dao;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Admin on 2017/2/21 09:42
 * 邮箱：tongzhenggang@126.com
 */

@Entity
public class Test {
    /**
     * SysMenuID : 578
     * SysMenuName : 娱乐频道
     * SysMenuNum : 0
     * SysMenuStr : 3
     * SysPID : 498
     * SortNum : 0
     * IsMenu : 0
     * MainID : 0
     * SysPStr : channel
     * SysMenuImage : /upload/menu/2016-10-24/Cut6ccb11b6dd234338b6f6ea5365dc91cd.jpg
     */

    private int SysMenuID;
    private String SysMenuName;
    private int SysMenuNum;
    private String SysMenuStr;
    private int SysPID;
    private int SortNum;
    private int IsMenu;
    private int MainID;
    private String SysPStr;
    private String SysMenuImage;
    @Generated(hash = 1129828404)
    public Test(int SysMenuID, String SysMenuName, int SysMenuNum, String SysMenuStr,
            int SysPID, int SortNum, int IsMenu, int MainID, String SysPStr,
            String SysMenuImage) {
        this.SysMenuID = SysMenuID;
        this.SysMenuName = SysMenuName;
        this.SysMenuNum = SysMenuNum;
        this.SysMenuStr = SysMenuStr;
        this.SysPID = SysPID;
        this.SortNum = SortNum;
        this.IsMenu = IsMenu;
        this.MainID = MainID;
        this.SysPStr = SysPStr;
        this.SysMenuImage = SysMenuImage;
    }
    @Generated(hash = 372557997)
    public Test() {
    }
    public int getSysMenuID() {
        return this.SysMenuID;
    }
    public void setSysMenuID(int SysMenuID) {
        this.SysMenuID = SysMenuID;
    }
    public String getSysMenuName() {
        return this.SysMenuName;
    }
    public void setSysMenuName(String SysMenuName) {
        this.SysMenuName = SysMenuName;
    }
    public int getSysMenuNum() {
        return this.SysMenuNum;
    }
    public void setSysMenuNum(int SysMenuNum) {
        this.SysMenuNum = SysMenuNum;
    }
    public String getSysMenuStr() {
        return this.SysMenuStr;
    }
    public void setSysMenuStr(String SysMenuStr) {
        this.SysMenuStr = SysMenuStr;
    }
    public int getSysPID() {
        return this.SysPID;
    }
    public void setSysPID(int SysPID) {
        this.SysPID = SysPID;
    }
    public int getSortNum() {
        return this.SortNum;
    }
    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }
    public int getIsMenu() {
        return this.IsMenu;
    }
    public void setIsMenu(int IsMenu) {
        this.IsMenu = IsMenu;
    }
    public int getMainID() {
        return this.MainID;
    }
    public void setMainID(int MainID) {
        this.MainID = MainID;
    }
    public String getSysPStr() {
        return this.SysPStr;
    }
    public void setSysPStr(String SysPStr) {
        this.SysPStr = SysPStr;
    }
    public String getSysMenuImage() {
        return this.SysMenuImage;
    }
    public void setSysMenuImage(String SysMenuImage) {
        this.SysMenuImage = SysMenuImage;
    }


}
