//package com.moa.global.vo;
//
//import com.example.smilekarina.global.exception.ErrorStateCode;
//import com.example.smilekarina.receipt.exception.ReceiptErrorStateCode;
//import com.example.smilekarina.user.exception.UserErrorStateCode;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class ErrorResponse {
//    private Boolean success;
//    private int code;
//    private String message;
//
//    public ErrorResponse(ErrorStateCode e) {
//        this.success = e.isSuccess();
//        this.code = e.getCode();
//        this.message = e.getMessage();
//    }
//
//    public ErrorResponse(UserErrorStateCode e) {
//        this.success = e.isSuccess();
//        this.code = e.getCode();
//        this.message = e.getMessage();
//    }
//
//    public ErrorResponse(ReceiptErrorStateCode e) {
//        this.success = e.isSuccess();
//        this.code = e.getCode();
//        this.message = e.getMessage();
//    }
//}
