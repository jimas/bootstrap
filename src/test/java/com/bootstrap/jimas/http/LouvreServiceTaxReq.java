package com.bootstrap.jimas.http;

public class LouvreServiceTaxReq {
    
    private String hotelCode; //hotelCode 第三方酒店code
    private Double amount;//服务费  比例 如：15D 对应  服务费占比15%。
    
    public String getHotelCode() {
        return hotelCode;
    }
    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    
    


}
