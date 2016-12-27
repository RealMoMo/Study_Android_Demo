package momo.com.day57_observer;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class MessageImage {

    public static final int FROM_A = 1;
    public static final int FROM_B = 2;

    private int fromType;
    private int imgId;

    public int getFromType() {
        return fromType;
    }

    public void setFromType(int fromType) {
        this.fromType = fromType;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }


}
