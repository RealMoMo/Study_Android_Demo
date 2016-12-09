package momo.com.day45_listview_vedio;

/**
 *
 */
public class VideoBean {
    private String videoUrl;
    private String coverUrl;
    private int width;
    private int height;
    private String title;

    public VideoBean() {
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VideoBean(String videoUrl, String coverUrl, int width, int height, String title) {
        this.videoUrl = videoUrl;
        this.coverUrl = coverUrl;
        this.width = width;
        this.height = height;
        this.title = title;
    }
}
