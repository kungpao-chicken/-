package com.shanke.forum.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class ImgUtil {

    public static boolean isImage(File imageFile) {
        if (!imageFile.exists()) {
            return false;
        }
        Image img;
        try {
            img = ImageIO.read(imageFile);
            return img != null && img.getWidth(null) > 0 && img.getHeight(null) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
