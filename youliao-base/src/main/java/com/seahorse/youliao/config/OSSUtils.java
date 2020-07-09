package com.seahorse.youliao.config;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.config
 * @ClassName: OssConfiguration
 * @Description: oss 配置 根据官网demo 自行按照业务改造
 * @author:songqiang
 * @Date:2020-03-16 11:36
 **/
@Slf4j
@Configuration
public class OSSUtils {


    /**
     * endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
     * 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
     * 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
     * endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
     * 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
     */
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    /**
     * accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
     * 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
     * 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
     */
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    /**
     * Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
     * Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
     */
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    /**
     * Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件。详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
     * Object命名规范如下：使用UTF-8编码，长度必须在1-1023字节之间，不能以“/”或者“\”字符开头。
     */
    @Value("${aliyun.oss.firstKey}")
    private String firstKey;


    /**
     * 创建存储空间
     */
    public void createBucket(String bucketName) {

        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        if (ossClient.doesBucketExist(bucketName)) {
            log.info("您已经创建Bucket：" + bucketName + "。");
        } else {
            log.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
            // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            ossClient.createBucket(bucketName);
        }

        // 关闭OSSClient。
        ossClient.shutdown();

    }


    /**
     * 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
     * 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
     *
     * @param bucketName
     * @return
     */
    public BucketInfo getBucketInfo(String bucketName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        BucketInfo info = ossClient.getBucketInfo(bucketName);
        log.info("Bucket " + bucketName + "的信息如下：");
        log.info("\t数据中心：" + info.getBucket().getLocation());
        log.info("\t创建时间：" + info.getBucket().getCreationDate());
        log.info("\t用户标志：" + info.getBucket().getOwner());

        // 关闭OSSClient。
        ossClient.shutdown();
        return info;
    }


    /**
     * 上传字符串
     */
    public void putStringObject(String content) {
        // 创建OSSClient实例。
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
            PutObjectRequest putObjectRequest = new PutObjectRequest("<yourBucketName>", "<yourObjectName>", new ByteArrayInputStream(content.getBytes()));

            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            log.info("result={}", result);
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

    }

    /**
     * 上传网络流
     */
    public void putURLInputStream() {
        OSS ossClient = null;

        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传网络流。
            InputStream inputStream = new URL("https://www.aliyun.com/").openStream();
            ossClient.putObject("<yourBucketName>", "<yourObjectName>", inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }

    /**
     * 上传文件流
     */
    public void putInputStream(InputStream inputStream,String objectName) {
        OSS ossClient = null;

        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            ossClient.putObject(bucketName, objectName, inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }

    /**
     * 上传本地文件
     */
    public void putLocalFile() {
        OSS ossClient = null;

        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest("<yourBucketName>", "<yourObjectName>", new File("<yourLocalFile>"));

            // 上传文件。
            ossClient.putObject(putObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }

    /**
     * 流式下载
     * @param objectName
     */
    public void getObjectInputstream(String objectName,HttpServletResponse response) throws IOException{

        // 创建OSSClient实例。
        OSS ossClient = null;
        OSSObject ossObject = null;
        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
             ossObject = ossClient.getObject(bucketName, objectName);

            OutputStream out = response.getOutputStream();
            InputStream inputStream = new BufferedInputStream(ossObject.getObjectContent());
            int buffer = 1024 * 10;
            byte[] data = new byte[buffer];

            response.setContentType("application/x-msdownload");
            //原始文件名
            String fileName = objectName.substring(objectName.lastIndexOf("/")+1);
            // 文件名可以任意指定
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            int read = 0;
            while ((read = inputStream.read(data)) != -1) {
                out.write(data, 0, read);
            }
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            ossObject.close();

        }

    }


    /**
     * 下载到本地
     */
    public void getObjectLocal(String objectName){

        // 创建OSSClient实例。
        OSS ossClient = null;
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("<yourLocalFile>"));

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     * 列举文件
     */
    public void listObjects(){


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        // objectListing.getObjectSummaries获取所有文件的描述信息。
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            log.info(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 删除文件
     * @param objectName
     */
    public void deleteObject(String objectName){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
