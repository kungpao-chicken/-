package com.shanke.forum.utils;

import org.junit.Test;

import java.io.File;

public class ImgUtilTest {

    @Test
    public void isImage() {
        File file = new File("/Users/wuchaojing/Desktop/手机相册/Screenshot_2019-01-16-12-59-35-44.png");
        System.out.println(ImgUtil.isImage(file));
    }

    @Test
    public void delete() {
        File file = new File("/Users/wuchaojing/Desktop/手机相册/Screenshot_2019-01-16-12-59-35-44.png");
        file.delete();
    }

}
