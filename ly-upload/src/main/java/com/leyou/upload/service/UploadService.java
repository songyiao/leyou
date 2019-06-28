package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;
    @Value("${image.allowTypes}")
    private String IMAGE_ALLOWTYPES;
    @Value("${image.server}")
    private String IMAGE_SERVER;

    public String uploadImage(MultipartFile file) {
        // 1)校验文件类型
        if(!IMAGE_ALLOWTYPES.contains(file.getContentType())){
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }
        // 2)校验图片内容
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
            return IMAGE_SERVER+storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件读取失败!",e);
        }
        return null;
    }
}
