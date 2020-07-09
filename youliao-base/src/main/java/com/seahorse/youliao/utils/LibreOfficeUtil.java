package com.seahorse.youliao.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.utils
 * @ClassName: LibreOfficeUtil
 * @Description: 第三方程序接入java 文档转为pdf
 * @author:songqiang
 * @Date:2020-05-06 15:20
 **/
public class LibreOfficeUtil {


    private static Logger logger = LoggerFactory.getLogger(LibreOfficeUtil.class);

    public static boolean convertPdf2(String sofficePath, File srcFile, File targetDir){
        ///Applications/LibreOffice.app/Contents/MacOS/soffice
        // --headless --invisible --convert-to
        // html --outdir pdf-html/ *.pdf

        Runtime runtime =Runtime.getRuntime();
        StringBuilder command = new StringBuilder();
        command.append(sofficePath);
        String outputDir = targetDir.getAbsolutePath();
        command.append(" --headless --invisible --convert-to pdf --outdir ");
        command.append(outputDir);
        command.append(" ");
        command.append(srcFile.getAbsolutePath());
        logger.info("开始转换pdf,命令行是:{}",command.toString());
        try {

            ProcessBuilder pb = new ProcessBuilder(command.toString());
            pb.redirectErrorStream(true);
            Process process = pb.start();
            printStream(process.getInputStream());
            int code = process.waitFor();
            logger.info("code:{}",code);

            File targetFile  = new File(targetDir,getFileNameWithoutSuffix(srcFile.getName())+".pdf");
            logger.info("目标文件是:{}",targetFile.getAbsolutePath());
            if(!targetFile.exists()){
                logger.info("文档转化失败");
                return false;
            }

        } catch (IOException e) {
            logger.error("convertPdf",e);
        } catch (InterruptedException e) {
            logger.error("convertPdf",e);
        }

        logger.info("文档转化成功");
        return true;
    }

    public static boolean convertPdf(String sofficePath,File srcFile, File targetDir){
        ///Applications/LibreOffice.app/Contents/MacOS/soffice
        // --headless --invisible --convert-to
        // html --outdir pdf-html/ *.pdf
        //Thread.sleep();
        List<String> commands=new ArrayList<>();
        String outputDir = targetDir.getAbsolutePath();
        commands.add(sofficePath);
        commands.add("--headless");
        commands.add("--invisible");
        commands.add("--convert-to");
        commands.add("pdf");
        commands.add("--outdir");
        commands.add(outputDir);
        commands.add(srcFile.getAbsolutePath());
        logger.info("开始转换pdf,命令行是:{}", Arrays.toString(commands.toArray()));
        try {

            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String result = IOUtils.toString(process.getInputStream(),"utf-8");
            logger.info("convertPdf from {} output {}:\n result:{}\n",srcFile.getAbsolutePath(),outputDir,result);
            process.destroy();

            File targetFile  = new File(targetDir,getFileNameWithoutSuffix(srcFile.getName())+".pdf");
            logger.info("目标文件是:{}",targetFile.getAbsolutePath());
            if(!targetFile.exists()){
                logger.info("文档转化失败");
                return false;
            }

        } catch (IOException e) {
            logger.error("convertPdf",e);
        }
        logger.info("文档转化成功");
        return true;
    }

    /**
     * 读取文件流
     * @param inputStream
     */
    public static void printStream(InputStream inputStream){
        new Thread(){
            @Override
            public void run() {
                logger.info("开始读取流,thread:{}",currentThread().getId());
                try {
                    String s = IOUtils.toString(inputStream,"utf-8");
                    logger.info("流内容:{}",s);
                } catch (IOException e) {
                    logger.error("读取流异常",e);
                }
                logger.info("结束读取流,thread:{}",currentThread().getId());
            }
        }.start();
    }

    /**
     * 获取没有后缀的文件名
     * @param fileName
     * @return
     */
    public static String getFileNameWithoutSuffix(String fileName){
        int pos=fileName.lastIndexOf(".");
        if(pos>=0){
            if(pos+1!=fileName.length()){
                return fileName.substring(0,pos);
            }
        }
        return "";
    }
}
