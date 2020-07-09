package com.seahorse.youliao.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * @description: ftp工具类
 * @author: Mr.Song
 * @create: 2019-12-24 21:59
 **/
public class FtpHelperw {


    private static Logger logger = LoggerFactory.getLogger(FtpHelperw.class);

    private static String ftp_host;
    private static Integer ftp_port;
    private static String ftp_user;
    private static String ftp_password;
    private static String ftp_url;

    static {

        ftp_host = null;
        ftp_port = null;
        ftp_user = null;
        ftp_password = null;
        ftp_url = null;
    }

    /**
     * 上传文件（可供Action/Controller层使用）
     *
     * @param pathName    FTP服务器保存目录
     * @param fileName    上传到FTP服务器后的文件名称
     * @param inputStream 输入文件流
     * @return
     */
    public static String uploadFile(String pathName, String fileName, InputStream inputStream) throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            //连接FTP服务器
            ftpClient.connect(ftp_host, ftp_port);
            //登录FTP服务器
            ftpClient.login(ftp_user, ftp_password);
            //是否成功登录FTP服务器
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new Exception("FTP服务器登录失败!");
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setRemoteVerificationEnabled(false);
            boolean flag = createDir(ftpClient, pathName);
            ftpClient.storeFile(fileName, inputStream);


            inputStream.close();
            ftpClient.logout();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String ftpUrl = ftp_url + "/" + pathName + "/" + fileName;
        return ftpUrl;
    }


    /**
     * 上传文件（可对文件进行重命名）
     *
     * @param pathName       FTP服务器保存目录
     * @param fileName       上传到FTP服务器后的文件名称
     * @param originFilename 待上传文件的名称（绝对地址）
     * @return
     */
    public String uploadFileFromProduction(String pathName, String fileName, String originFilename) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(originFilename));
            return uploadFile(pathName, fileName, inputStream);
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 上传文件（不可以进行文件的重命名操作）
     *
     * @param pathName       FTP服务器保存目录
     * @param originFilename 待上传文件的名称（绝对地址）
     * @return
     */
    public static String uploadFileFromProduction(String pathName, String originFilename) throws Exception {
        try {
            String fileName = new File(originFilename).getName();
            InputStream inputStream = new FileInputStream(new File(originFilename));
            return uploadFile(pathName, fileName, inputStream);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * 删除文件
     *
     * @param pathName FTP服务器保存目录
     * @param fileName 要删除的文件名称
     * @return
     */
    public static boolean deleteFile(String pathName, String fileName) throws Exception {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            //连接FTP服务器
            ftpClient.connect(ftp_host, ftp_port);
            //登录FTP服务器
            ftpClient.login(ftp_user, ftp_password);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathName);
            ftpClient.dele(fileName);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param pathName  FTP服务器文件目录
     * @param fileName  文件名称
     * @param localPath 下载后的文件路径
     * @return
     */
    public static boolean downloadFile(String pathName, String fileName, String localPath) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            //连接FTP服务器
            ftpClient.connect(ftp_host, ftp_port);
            //登录FTP服务器
            ftpClient.login(ftp_user, ftp_password);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathName);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (fileName.equalsIgnoreCase(file.getName())) {
                    OutputStream os = null;
                    try {
                        File localFile = new File(localPath + "/" + file.getName());
                        os = new FileOutputStream(localFile);
                        ftpClient.retrieveFile(file.getName(), os);
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (os != null) {
                            os.close();
                        }
                    }
                }
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 创建目录(有则切换目录，没有则创建目录)
     *
     * @param dir
     * @return
     */
    public static boolean createDir(FTPClient ftp, String dir) {
        String d;
        try {
            //目录编码，解决中文路径问题
            d = new String(dir.toString().getBytes("GBK"), "iso-8859-1");
            //尝试切入目录
            if (ftp.changeWorkingDirectory(d)) {
                return true;
            }
            String[] arr = dir.split("\\/");
            StringBuffer sbfDir = new StringBuffer();
            //循环生成子目录
            for (String s : arr) {
                if (StringUtils.isEmpty(s)) {
                    continue;
                }
                sbfDir.append("/");
                sbfDir.append(s);
                //目录编码，解决中文路径问题
                d = new String(sbfDir.toString().getBytes("GBK"), "iso-8859-1");
                //尝试切入目录
                if (ftp.changeWorkingDirectory(d)) {
                    continue;
                }
                if (!ftp.makeDirectory(d)) {
                    logger.info("[失败]ftp创建目录：" + sbfDir.toString());
                    return false;
                }
                logger.info("[成功]创建ftp目录：" + sbfDir.toString());
            }

            //将目录切换至指定路径
            return ftp.changeWorkingDirectory(d);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String pdfupload(String fileName, InputStream fileIn,String type) throws Exception {

        String fileUrl = "";
        //每天一个文件夹

        //获取文件基于项目相对路径
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()){
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(),"statics/uploads/files/"+type+"/");
        if(!upload.exists()){
            upload.mkdirs();
        }

        String applyPath = ResourceUtils.getURL("classpath:").getPath()+"statics/uploads/files/" + type + "/";

        //文件绝对路径
        String absFileUrl = applyPath + fileName;
        //相对路径
        fileUrl = "/uploads/files/" + type + "/" + fileName;
        File uploadFile = new File(absFileUrl);
        logger.info("上传后文件路径:" + absFileUrl);
        //上传
        FileCopyUtils.copy(readStream(fileIn), uploadFile);
        return fileUrl;
    }


    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }


    public static void main(String[] args) {
        try {
            File file = new File("F:\\transportImage.jpg");
            FileInputStream fis = new FileInputStream(file);
            String ftpUrl = FtpHelperw.uploadFile("/wms/job_sign", "transportImage.jpg", fis);
            System.out.println(ftpUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
