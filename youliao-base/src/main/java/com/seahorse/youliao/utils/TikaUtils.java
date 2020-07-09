package com.seahorse.youliao.utils;

import jodd.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.tika.detect.DefaultEncodingDetector;
import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.utils
 * @ClassName: TikaUtils
 * @Description: 文档提取工具类
 * 下载相关地址：https://mirrors.tuna.tsinghua.edu.cn/apache/tika/
 * @author:songqiang
 * @Date:2020-05-06 15:13
 **/
public class TikaUtils {

    private static Logger logger= LoggerFactory.getLogger(TikaUtils.class);
    public static String parseContent(File f){
        String content =null;
        try{
            InputStream stream = FileUtils.openInputStream(f);
            content =parseContent(stream);
            if(StringUtil.isEmpty(content)){
                content=parseTxt(f);
            }
            if (content != null) {
                return content;
            }
        }catch (Exception e){
            logger.error("tika parse error",e);
        }
        return content;
    }

    private static String parseTxt(File file) throws IOException {
        InputStream stream1 = FileUtils.openInputStream(file);
        EncodingDetector detector=new DefaultEncodingDetector();
        Charset charset = detector.detect(new BufferedInputStream(stream1), new Metadata());
        stream1.close();
        if(charset!=null){
            return FileUtils.readFileToString(file,charset);
        }else {
            return null;
        }
    }

    public static String parseContent(InputStream stream){
        String content =null;
        try{
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
            Metadata metadata = new Metadata();
            parser.parse(stream, handler, metadata);
            content = handler.toString();
        }catch (Exception e){
            logger.error("tika parse error",e);
        }
        return content;
    }
}
