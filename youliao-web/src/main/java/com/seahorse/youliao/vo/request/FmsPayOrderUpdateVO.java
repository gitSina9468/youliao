package com.seahorse.youliao.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
/**
* describe: 支付订单
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
@ApiModel()
@Getter
@Setter
@ToString
public class FmsPayOrderUpdateVO {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
	private Long id;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    @NotBlank(message = "商品名称不能为空")
    @Length(max = 36,message = "商品名称长度最大36")
	private String productName;
    /**
     * 商品描述
     */
    @ApiModelProperty("商品描述")
    @NotBlank(message = "商品描述不能为空")
    @Length(max = 200,message = "商品描述长度最大200")
	private String description;

    /**
     * 缴费金额
     */
    @ApiModelProperty("缴费金额")
    @NotNull(message = "缴费金额不能为空")
    @DecimalMin(value = "0.01",message = "缴费金额最小0.01元")
	private BigDecimal payFee;

    /**
     * 缴费方式 1 微信 2 支付宝
     */
    @ApiModelProperty("缴费方式 1 微信 2 支付宝")
    @NotNull(message = "缴费方式不能为空")
    @Min(value = 1,message = "缴费方式错误")
    @Max(value = 2,message = "缴费方式错误")
	private Integer payType;


}
