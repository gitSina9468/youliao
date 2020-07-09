package com.seahorse.youliao.vo.request.ws;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.vo.request.ws
 * @ClassName: GuardQueueInfo
 * @Description: TODO
 * @author:songqiang
 * @Date:2020-01-10 17:18
 **/
@Getter
@Setter
public class GuardQueueInfo {

    private String id;
    private String factoryId;
    private String factoryName;
    private String warehouseName;
    private String startAddr;
    private String endAddr;
    private String skuName;
    private String statusName;
    private Integer status;
    private String carNo;
    private String guardName;
    private Integer waitNum;
    private Date caretTime;
    private String routeCode;
    private String warehouseId;
}
