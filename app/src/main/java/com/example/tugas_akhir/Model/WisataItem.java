package com.example.tugas_akhir.Model;

public class WisataItem {

    private int imageResource;
    private String title;
    private String desc;
    private String key_id;
    private String favStatus;

    public WisataItem() {
    }

    public WisataItem(int imageResource, String title, String desc, String key_id, String favStatus) {
        this.imageResource = imageResource;
        this.title = title;
        this.desc = desc;
        this.key_id = key_id;
        this.favStatus = favStatus;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }
}
