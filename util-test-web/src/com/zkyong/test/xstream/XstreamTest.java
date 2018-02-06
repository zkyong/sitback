package com.zkyong.test.xstream;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XstreamTest {
    public static void main(String[] args) {
//        String xml = obj2Xml();
//        System.out.println(xml);
//        
//        User user = (User) xml2Obj(xml);
//        System.out.println(user);
        List<String> list=new ArrayList<String>();
        list.add("保护环境");     //向列表中添加数据
        list.add("爱护地球");     //向列表中添加数据
        list.add("从我做起");        //向列表中添加数据
        list.add(0,"1从我做起");     //在第1+1个元素的位置添加数据
        //通过循环输出列表中的内容
        for(int i=0;i<list.size();i++){
         System.out.println(i+":"+list.get(i));
        }
    }
    
    
    public static String obj2Xml(){
        User user = new User();
        user.setUsername("张三");
        user.setPassword("mypwd");
        user.setRegtime(new Date());
        List<String> annexes = new ArrayList<>();
        annexes.add("附件一");
        annexes.add("附件二");
        Url url = new Url();
        url.setUrl(annexes);
        
        user.setAnnexes(url);
        
        XStream xstream = new XStream();  
        xstream.processAnnotations(User.class);  
        xstream.processAnnotations(Url.class);  
        return xstream.toXML(user);
    }
    
    public static Object xml2Obj(String xml){
        ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
        XStream xstream=new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.alias("Request", User.class);
        xstream.autodetectAnnotations(true);
        return xstream.fromXML(in);
    }
}
