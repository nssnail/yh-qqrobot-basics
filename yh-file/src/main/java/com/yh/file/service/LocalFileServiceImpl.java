package com.yh.file.service;

import com.yh.common.config.RuoYiConfig;
import com.yh.common.utils.file.FileUploadUtils;
import com.yh.file.config.FileConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wyh
 * @Description TODO
 * @createTime 2023年05月03日 11:35:00
 */
@Service("LocalFileService")
@Slf4j
public class LocalFileServiceImpl implements ISysFileService {

    @Autowired
    private FileConfig fileConfig;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件名称
        String fileName = FileUploadUtils.upload(filePath, file);
        return fileConfig.getUrl() + fileName;
    }
}