package com.yh.file.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import com.yh.file.config.TxConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 14:52:00
 */
@Slf4j
@Component
public class TxFileUtil {

    @Autowired
    private TxConfig txConfig;


    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private TransferManager createTransferManager() {
        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        COSClient cosClient = createCOSClient();

        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(32);

        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);

        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1 * 1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);

        return transferManager;
    }

    private void shutdownTransferManager(TransferManager transferManager) {
        // 指定参数为 true, 则同时会关闭 transferManager 内部的 COSClient 实例。
        // 指定参数为 false, 则不会关闭 transferManager 内部的 COSClient 实例。
        transferManager.shutdownNow(true);
    }

    public COSClient createCOSClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(txConfig.getSecretId(), txConfig.getSecretKey());
        Region region = new Region(txConfig.getRegionName());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }


    public String upload(MultipartFile file) throws IOException {
        TransferManager transferManager = createTransferManager();
        String projectName=txConfig.getProjectName();
        if(StringUtils.isNoneBlank(projectName)){
            projectName="base";
        }
        String fileName = file.getOriginalFilename();
        String uploadName = UUID.randomUUID().toString().replace("-", "");
        // 文件后缀
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index + 1);

        //文件名+后缀
        String name = uploadName + "." + suffix;

        String data = sdf.format(new Date());
        // 指定文件将要存放的存储桶
        String bucketName = txConfig.getBucketName();
        String url="/"+data + "/" + name;
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = projectName+url;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), new ObjectMetadata());
        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
        } catch (CosServiceException e) {
            log.error("COS服务异常");
        } catch (CosClientException e) {
            log.error("COS客户端异常");
        } catch (InterruptedException e) {
            log.error("其他异常");
        }

        // 确定本进程不再使用 transferManager 实例之后，关闭之
        // 详细代码参见本页：高级接口 -> 关闭 TransferManager
        shutdownTransferManager(transferManager);
        return getUrl(key);
    }


    public String uploadBase64(byte[] bytes) {
        TransferManager transferManager = createTransferManager();
        String projectName=txConfig.getProjectName();
        if(StringUtils.isNoneBlank()){
            projectName="base";
        }
        String uploadName = UUID.randomUUID().toString().replace("-", "")+".png";

        String data = sdf.format(new Date());
        // 指定文件将要存放的存储桶
        String bucketName = txConfig.getBucketName();
        String url="/"+data + "/" + uploadName;
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = projectName+url;
        InputStream inputStream=new ByteArrayInputStream(bytes);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, new ObjectMetadata());
        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
        } catch (CosServiceException e) {
            log.error("COS服务异常");
        } catch (CosClientException e) {
            log.error("COS客户端异常");
        } catch (InterruptedException e) {
            log.error("其他异常");
        }

        // 确定本进程不再使用 transferManager 实例之后，关闭之
        // 详细代码参见本页：高级接口 -> 关闭 TransferManager
        shutdownTransferManager(transferManager);
        return getUrl(key);
    }

    public String getUrl(String fileName) {
        // 不需要验证身份信息
        COSCredentials cred = new AnonymousCOSCredentials();

        ClientConfig clientConfig = new ClientConfig();

        clientConfig.setRegion(new Region(txConfig.getRegionName()));

        clientConfig.setHttpProtocol(HttpProtocol.https);

        COSClient cosclient = new COSClient(cred, clientConfig);

        URL objectUrl = cosclient.getObjectUrl(txConfig.getBucketName(), fileName);
        return objectUrl.toString();
    }

}