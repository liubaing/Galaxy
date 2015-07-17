package com.liubaing.galaxy.web.support;

import com.liubaing.galaxy.exception.FileUploadException;
import com.liubaing.galaxy.util.Constants;
import com.liubaing.galaxy.util.IDGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class FileProcessor {

    @Value("#{webConfig['filePath']}")
    private String filePath;

    /**
     * Spring容器启动创建目录，异常直接抛出
     *
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        FileUtils.forceMkdir(new File(filePath));
    }

    private String copyFile(MultipartFile src, String destDir) {
        String suffix = FilenameUtils.getExtension(src.getOriginalFilename());
        //按时间排序
        String newFileName = System.currentTimeMillis() + Constants.UNDERLINE + IDGenerator.generate()
                + FilenameUtils.EXTENSION_SEPARATOR_STR + suffix;
        String path = FilenameUtils.concat(destDir, newFileName);
        try {
            //TODO 切图
            src.transferTo(new File(path));
            return newFileName;
        } catch (IllegalStateException e) {
            throw new FileUploadException("文件上传失败，路径为" + path, e);
        } catch (IOException e) {
            throw new FileUploadException("文件存储失败", e);
        }
    }
}
