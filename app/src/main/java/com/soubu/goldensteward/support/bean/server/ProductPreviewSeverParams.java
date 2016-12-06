package com.soubu.goldensteward.support.bean.server;

/**
 * Created by dingsigang on 16-11-28.
 */

public class ProductPreviewSeverParams {
    String url;
    String title;
    String price;
    String browser_volume;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrowser_volume() {
        return browser_volume;
    }

    public void setBrowser_volume(String browser_volume) {
        this.browser_volume = browser_volume;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
