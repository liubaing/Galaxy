package com.liubaing.galaxy.web.support;

import com.liubaing.galaxy.exception.FileUploadException;
import com.liubaing.galaxy.util.IDGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

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
        LocalDate now = LocalDate.now();
        //按时间排序
        String filePath = Paths.get(String.valueOf(now.getYear()), String.valueOf(now.getMonth()), String.valueOf(now.getDayOfMonth())).toString();
        String absolutePath = FilenameUtils.concat(destDir, filePath);
        String fileName = IDGenerator.generate() + FilenameUtils.EXTENSION_SEPARATOR_STR + suffix;
        try {
            File destFile = new File(absolutePath);
            FileUtils.forceMkdir(destFile);
            //TODO 切图，尺寸处理
            src.transferTo(new File(destFile, fileName));
            return FilenameUtils.concat(filePath, fileName);
        } catch (IllegalStateException e) {
            throw new FileUploadException("文件上传失败，路径为[" + absolutePath + "]", e);
        } catch (IOException e) {
            throw new FileUploadException("文件存储失败", e);
        }
    }
}
