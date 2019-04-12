package com.zkyong.demo.other;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class Test {
    public static void main(String[] args) throws MalformedURLException {
        final String url = "http://localhost:8080/Hessian/service/animalService";
        HessianProxyFactory factory = new HessianProxyFactory();
        IAnimalService proxy = (IAnimalService) factory.create(IAnimalService.class, url);
        System.out.println(proxy.getMonkeyName()); 
    }
}
