package com.bluedot.resource.vo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;

/**
 * @author Jason
 * @creationDate 2023/07/23 - 15:52
 */
public class UploadFile {
    private FormDataContentDisposition userImg;
    private InputStream userImgStream;

    public UploadFile() {
    }

    public UploadFile(FormDataContentDisposition userImg, InputStream userImgStream) {
        this.userImg = userImg;
        this.userImgStream = userImgStream;
    }

    public FormDataContentDisposition getUserImg() {
        return userImg;
    }

    public void setUserImg(FormDataContentDisposition userImg) {
        this.userImg = userImg;
    }

    public InputStream getUserImgStream() {
        return userImgStream;
    }

    public void setUserImgStream(InputStream userImgStream) {
        this.userImgStream = userImgStream;
    }
}
