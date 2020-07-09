package com.seahorse.youliao.entity.freemarker;

/**
 * @description: 业务合同明细
 * @author: Mr.Song
 * @create: 2019-12-24 21:25
 **/
public class ContractBusinessDetailsDto {


    /**
     *  配载明细id
     */
    private String id;
    /**
     * 主键
     */
    private String stowageOrderId;

    /**
     * OMS单号
     */
    private String omsNum;

    /**
     * 物料名称
     */
    private String skuName;
    /**
     * 单位名称
     */
    private String unitName;
    /***
     * 规格名称
     */
    private  String specName;
    /**
     * 数量
     */
    private String piecesQuantity;

    /**
     * 重量
     */
    private String weightQuantity;

    /**
     * 体积
     */
    private String volumeQuantity;

    /**
     * 明细展示数量
     */
    private String quantity;

    /**
     * 要求发货时间
     */
    private String deliveryTime;

    /**
     * 省
     */
    private String provinceName;

    /**
     * 市
     */
    private String cityName;

    /**
     * 区县
     */
    private String countyName;

    /**
     * 详细 地址
     */
    private String consigneeAddress;

    /**
     * 备注
     * @return
     */
    private String remark;

    //erp订单
    private String erpNum;

    //经销商id
    private String dealerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId==null?"":dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getErpNum() {
        return erpNum;
    }

    public void setErpNum(String erpNum) {
        this.erpNum = erpNum;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getStowageOrderId() {
        return stowageOrderId;
    }

    public void setStowageOrderId(String stowageOrderId) {
        this.stowageOrderId = stowageOrderId;
    }

    public String getOmsNum() {
        return omsNum;
    }

    public void setOmsNum(String omsNum) {
        this.omsNum = omsNum;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getPiecesQuantity() {
        return piecesQuantity.replace(",","");
    }

    public void setPiecesQuantity(String piecesQuantity) {
        this.piecesQuantity = piecesQuantity;
    }

    public String getWeightQuantity() {
        return weightQuantity;
    }

    public void setWeightQuantity(String weightQuantity) {
        this.weightQuantity = weightQuantity;
    }

    public String getVolumeQuantity() {
        return volumeQuantity;
    }

    public void setVolumeQuantity(String volumeQuantity) {
        this.volumeQuantity = volumeQuantity;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }
}
