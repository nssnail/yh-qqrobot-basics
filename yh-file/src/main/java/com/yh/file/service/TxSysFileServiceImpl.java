package com.yh.file.service;

import com.yh.file.util.TxFileUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2022年02月18日 14:49:00
 */
@Data
@Service
@Primary
public class TxSysFileServiceImpl implements ISysFileService {
    @Autowired
    private TxFileUtil txFileUtil;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        return txFileUtil.upload(file);
    }
}