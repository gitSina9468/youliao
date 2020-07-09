package com.seahorse.youliao.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.utils
 * @ClassName: GsonFactory
 * @Description: gson 工厂
 * @author:songqiang
 * @Date:2020-03-26 10:49
 **/
public class GsonFactory {

    private static class GsonHolder {

        private static Gson gson = new GsonBuilder()
                //设置驼峰、下划线命名互转
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public static Gson getGson() {
        return GsonHolder.gson;
    }
}
