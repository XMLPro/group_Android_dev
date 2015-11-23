package com.example.suzuki.memoprot001;

/**
 * Created by Admin on 2015/11/09.
 */

import jp.ne.docomo.smt.dev.characterrecognition.constants.ImageContentType;
import jp.ne.docomo.smt.dev.characterrecognition.constants.Lang;

public class RecognitionAsyncTaskParam {
    private ImageContentType imageType;
    private Lang lang;
    private String imagePath;
    private String jobId;
    private String APIkey = "794257507342303944574f76634c55636d574e67726b52724d71364f505647713351573073596b516f2e42";

    public ImageContentType getImageType() {
        return imageType;
    }

    public void setImageType(ImageContentType imageType) {
        this.imageType = imageType;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getKey() {
        return APIkey;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }


}
