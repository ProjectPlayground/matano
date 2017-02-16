package matano.apkode.net.matano.model;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Programme {
    private String title;
    private String subTitle;
    private String image;
    private String content;

    public Programme() {
    }


    public Programme(String title, String subTitle, String image, String content) {
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
