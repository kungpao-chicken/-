package com.shanke.forum.remote;

import com.shanke.forum.utils.ImgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class UploadSrv {

    @Value("${shell_path}")
    private String shellPath;

    public String uploadFile(MultipartFile file) {
        String imgNameNew = UUID.randomUUID().toString();
        File tmpFile = new File("/data/forum/" + imgNameNew);
        try {
            tmpFile.createNewFile();
        } catch (IOException e) {
            log.error("fail to create file {}", tmpFile, e);
            return null;
        }
        try {
            file.transferTo(tmpFile);
        } catch (IOException e) {
            log.error("fail to upload avatar", e);
        }
        if (!ImgUtil.isImage(tmpFile)) {
            tmpFile.delete();
            return "not-image";
        }

        return uploadFileRemote(tmpFile, imgNameNew);

    }

    private String uploadFileRemote(File tmpFile, String imgNameNew) {
        try {
            String[] shPath = new String[]{shellPath, tmpFile.getPath()};
            Process ps = Runtime.getRuntime().exec(shPath);
            ps.waitFor();
        } catch (Exception e) {
            log.error("fail to exec scp.sh", e);
            return null;
        } finally {
            tmpFile.delete();
        }
        return imgNameNew;
    }

}
