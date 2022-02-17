package com.zls.atcrowdfunding.bean;

import java.util.ArrayList;
import java.util.List;

public class TMenu {
    private Long id;

    private Long pid;

    private String name;

    private String icon;

    private String url;

    private List<TMenu> childMenus = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public List<TMenu> getChildMenus(){return childMenus;}

    @Override
    public String toString() {
        return "TMenu{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", childMenus=" + childMenus +
                '}';
    }
}