package com.seahorse.youliao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

/**
 * @description: json 读取工具类
 * @author: Mr.Song
 * @create: 2020-01-27 19:36
 **/
public class JsonResourceUtils {


    private static Logger logger = LoggerFactory.getLogger(JsonResourceUtils.class);

    private JsonResourceUtils() {

    }

    /**
     * 读取json 对象
     * @param filename
     * @return
     */
    public static JSONObject getJsonObjFromResource(String filename) {
        JSONObject json = null;

        if (!filename.contains(".json")) {
            filename += ".json";
        }

        try {

            File file = getFile(filename);
            if (file.exists()) {
                String content = FileUtils.readFileToString(file, "UTF-8");
                json = JSON.parseObject(content);
            } else {
                logger.info("file not exist!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("readFileToString" + e.getMessage());
        }


        return json;
    }

    /**
     * 读取json 数组
     * @param filename
     * @return
     */
    public static JSONArray getJsonArrayFromResource(String filename) {
        JSONArray json = null;

        if (!filename.contains(".json")) {
            filename += ".json";
        }

        try {

            File file = getFile(filename);
            if (file.exists()) {
                String content = FileUtils.readFileToString(file, "UTF-8");
                json = JSON.parseArray(content);
            } else {
                logger.info("file not exist!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("readFileToString" + e.getMessage());
        }


        return json;
    }

    private static File getFile(String filename) {
        URL url = JsonResourceUtils.class.getResource(filename);
        String path = url.getPath();
        return new File(path);
    }
}
