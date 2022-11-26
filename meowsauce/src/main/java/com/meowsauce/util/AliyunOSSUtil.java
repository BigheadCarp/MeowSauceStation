package com.meowsauce.util;


import com.aliyun.oss.*;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class AliyunOSSUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);

    /**
     * 上传文件
     */
    public String upLoad(File file) {
        logger.info("------OSS文件上传开始--------" + file.getName());
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        //这里endpoint 在你的bucket列表->点击你的bucket->点击概览中间就有，下面有截图
        System.out.println("获取到的Point为:" + endpoint);
        String accessKeyId = "";    //accessKeyId 、accessKeySecret 上面有说到哪里获取
        String accessKeySecret = "";
        String bucketName = "";  //刚才新建的bucket名称
        String fileHost = " ";   //在刚才新建的bucket下面新建一个目录，这就是那个目录的名称
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
        // 判断文件
        if (file == null) {
            return null;
        }
        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }

            String suffixName = file.getName().substring(file.getName().lastIndexOf("."));
            // 设置文件路径和名称
            String fileUrl = fileHost + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + suffixName);
            // 上传文件
            PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                String url = "https://meowsauce.oss-cn-hangzhou.aliyuncs.com/" + fileUrl;
                logger.info("------OSS文件上传成功------" + "https://meowsauce.oss-cn-hangzhou.aliyuncs.com/" + fileUrl);
                return  url;
            }
        } catch (OSSException oe) {
            logger.error(oe.getMessage());
        } catch (ClientException ce) {
            logger.error(ce.getErrorMessage());
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
        return null;
    }
}
