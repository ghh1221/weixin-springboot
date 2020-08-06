package com.hh.dto;

        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.databind.annotation.JsonSerialize;
        import com.hh.dataobject.OrderDetail;
        import com.hh.enums.OrderStatusEnum;
        import com.hh.enums.PayStatusEnum;
        import com.hh.serializer.Data2Serializer;
        import com.hh.util.EnumUtil;
        import lombok.Data;

        import javax.persistence.Id;
        import java.math.BigDecimal;
        import java.util.Date;
        import java.util.List;

/**
 * 数据传输对象，在各个层之间传输使用，这里为了加上orderDetailList
 * 因为数据库中没有这个字段，所以不能加在OrderDetail类中
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    //订单Id
    @Id
    private String orderId;

    //买家名字
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信openid
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态
    private Integer orderStatus;

    //支付状态
    private Integer payStatus;

    @JsonSerialize(using = Data2Serializer.class)
    //创建时间
    private Date createTime;

    @JsonSerialize(using = Data2Serializer.class)
    //更新时间
    private Date updateTime;

    //订单详情列表
    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
