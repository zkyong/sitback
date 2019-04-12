package com.zkyong.test.xstream;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Url {

    @XStreamImplicit(itemFieldName="Url")
    private List<String> url;

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Url [url=" + url + "]";
    }
}
