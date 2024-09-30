package com.example.shoppingplatform.exception;

public class CommodityNotFoundException extends RuntimeException {
    public CommodityNotFoundException(String message) {
        super(message);
    }
}
