package com.seahorse.youliao.logfilter;


import java.lang.annotation.*;

/**
 * @ProjectName: jinkai-parent
 * @Package: com.scil.jinkai.log
 * @ClassName: Log
 * @Description: 日志接口
 * @author:songqiang
 * @Date:2020-01-15 15:15
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {


    /**
     * 操作说明
     * @return
     */
    String description() default "";

    /**
     * 操作类型
     * @return
     */
    OperationType type() default OperationType.SELECT;

    /**
     * 按钮
     * @return
     */
    String button();

    /**
     * 菜单
     * @return
     */
    String menu();


    enum OperationType{

        //搜索
        SELECT,
        //新增
        ADD,
        //编辑
        UPDATE,
        //删除
        DELETE,
        //支付
        PAY,
        //退款
        REFUND,
        //下载
        DOWNLOAD,
        //上传
        UPLOAD;

    }




}
