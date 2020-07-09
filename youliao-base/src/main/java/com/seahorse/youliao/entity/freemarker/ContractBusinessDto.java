package com.seahorse.youliao.entity.freemarker;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 业务合同
 * @author: Mr.Song
 * @create: 2019-12-24 21:24
 **/
public class ContractBusinessDto {



    /**
     * 运单ID
     */
    private String stowageOrderId;
    /**
     * 配载单号
     */
    private String stowageNum;
    /**
     * 运单号
     */
    private String waybillNum;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 司机姓名
     */
    private String driverName;


    /**
     * 联系电话
     */
    private String driverContract;

    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 收货省
     */
    private String toProvinceName;

    /**
     * 收货市
     */
    private String toCityName;

    /**
     * 收货区
     */
    private String toCountryName;

    /**
     * 收货人
     */
    private String consigneeName;

    /**
     * 收货人电话
     */
    private String consigneeContact;

    /**
     * 发货人电话
     */
    private String deliveryContact;
    /***
     * 收货详细地址
     */
    private String consigneeAddress;


    /**
     * 承运单位
     */
    private String quotOrganName;

    /***
     * 运输方式
     */
    private String  transportType;

    /**
     * 提货方式
     */
    private  String  pickType;

    /***
     * 发货时间
     */
    private String deliveryTime;


    /**
     * 服务地址
     */
    private String ngxServerAddress;

    /**
     * 指纹图片
     */
    private String fingerPrintImages;

    /**
     * 签字图片
     */
    private String signImages;
    /**
     * 明细合计
     */
    private String sumNum;

    /**
     * 二维码地址
     * @return
     */
    private String codeUrl;

    private String version;//用于判断tms配载单是否修改

    private String oppointment;//预约信息

    //经销商id
    private String dealerId;
    //合同编号
    private String versionNum;

    private String orderType;

    /**
     * 合同业务明细
     */
    public List<ContractBusinessDetailsDto> mvDetails= new ArrayList<>(5);

    public List<ContractBusinessDetailsDto> getMvDetails() {
        return mvDetails;
    }

    public void setMvDetails(List<ContractBusinessDetailsDto> mvDetails) {
        List<ContractBusinessDetailsDto> itme=new ArrayList<>();
        for (int i=1;i<6;i++){
            if(i<=mvDetails.size()){
                itme.add(mvDetails.get(i-1));
            }else {
                itme.add(new ContractBusinessDetailsDto());
            }
        }
        this.mvDetails = itme;
    }

    /**
     * 门卫留存明细
     */
    public List<ContractBusinessDetailsDto> detailsDtoList = new ArrayList<>();

    public List<ContractBusinessDetailsDto> getDetailsDtoList() {
        return detailsDtoList;
    }

    public void setDetailsDtoList(List<ContractBusinessDetailsDto> detailsDtoList) {
        this.detailsDtoList = detailsDtoList;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getOppointment() {
        return oppointment;
    }

    public void setOppointment(String oppointment) {
        this.oppointment = oppointment;
    }

    public String getStowageNum() {
        return stowageNum;
    }

    public void setStowageNum(String stowageNum) {
        this.stowageNum = stowageNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryContact() {
        return deliveryContact;
    }

    public void setDeliveryContact(String deliveryContact) {
        this.deliveryContact = deliveryContact;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getSumNum() {
        return sumNum;
    }

    public void setSumNum(String sumNum) {
        this.sumNum = sumNum;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getPickType() {
        return pickType;
    }

    public void setPickType(String pickType) {
        this.pickType = pickType;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeContact() {
        return consigneeContact;
    }

    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getWaybillNum() {
        return waybillNum;
    }

    public void setWaybillNum(String waybillNum) {
        this.waybillNum = waybillNum;
    }

    public String getNgxServerAddress() {
        return ngxServerAddress;
    }

    public void setNgxServerAddress(String ngxServerAddress) {
        this.ngxServerAddress = ngxServerAddress;
    }


    public String getFingerPrintImages() {
        return fingerPrintImages;
    }

    public void setFingerPrintImages(String fingerPrintImages) {
        this.fingerPrintImages = fingerPrintImages;
    }

    public String getSignImages() {
        return signImages;
    }

    public void setSignImages(String signImages) {
        this.signImages = signImages;
    }


    public String getStowageOrderId() {
        return stowageOrderId;
    }

    public void setStowageOrderId(String stowageOrderId) {
        this.stowageOrderId = stowageOrderId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getDriverContract() {
        return driverContract;
    }

    public void setDriverContract(String driverContract) {
        this.driverContract = driverContract;
    }

    public String getToProvinceName() {
        return toProvinceName;
    }

    public void setToProvinceName(String toProvinceName) {
        this.toProvinceName = toProvinceName;
    }

    public String getToCityName() {
        return toCityName;
    }

    public void setToCityName(String toCityName) {
        this.toCityName = toCityName;
    }

    public String getToCountryName() {
        return toCountryName;
    }

    public void setToCountryName(String toCountryName) {
        this.toCountryName = toCountryName;
    }

    public String getQuotOrganName() {
        return quotOrganName;
    }

    public void setQuotOrganName(String quotOrganName) {
        this.quotOrganName = quotOrganName;
    }
}
