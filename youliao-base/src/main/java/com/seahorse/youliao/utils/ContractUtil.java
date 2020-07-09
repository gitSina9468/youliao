package com.seahorse.youliao.utils;

import com.seahorse.youliao.entity.freemarker.ContractBusinessDetailsDto;
import com.seahorse.youliao.entity.freemarker.ContractBusinessDto;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 合同工具类
 * @author: Mr.Song
 * @create: 2019-12-24 21:55
 **/
public class ContractUtil {


    /**
     * 根据模板生成合同
     *
     * @param dto 数据实体
     * @return 返回
     */
    public static String createTemplateContract(ContractBusinessDto dto, InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        builder.append(createMainContract(dto, inputStream));

        return builder.toString();
    }

    /**
     * 生成分页语句
     *
     * @return
     */
    public static String createSplitPage() {
        return "<para breakPage=\"true\"/>";
    }


    /**
     * 创建主模板合同
     *
     * @param dto          数据源
     * @return 返回字符串
     */
    private static String createMainContract(ContractBusinessDto dto, InputStream inputStream) {
        StringBuffer buffer = new StringBuffer();

        //设置模板变量
        Map<String, Object> map = new HashMap<>(16);
        map.put("model", dto);
        //获取年份
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        int random = (int) ((Math.random() * 9 + 1) * 100);

        map.put("ContractCode", sdf.format(date) + random);
        List<ContractBusinessDetailsDto> detailsDtoList = dto.detailsDtoList;
        map.put("detailsList", detailsDtoList);
        map.put("count", detailsDtoList.size());

        String fileName = "contract.ftl";

        String content = FreeMarkerUtil.templateAnalysis(map, inputStream, fileName);

        buffer.append(content);

        return buffer.toString();
    }
}
