package com.distribution.common.utils;

public class MoneyUtil {
   
    
    public static Long convertMoney2Cent(String yuan){
    	Money money = new Money(yuan);
    	return money.getCent();
    }
    
    public static void main(String[]  args){
       System.out.println(convertMoney2Cent("20.02"));
    }
    
}
