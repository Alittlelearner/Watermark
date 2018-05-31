package controller;

import java.io.File;
import java.util.Map;

import tool.ImageUtil;
import tool.WaterMakUtil;

public class WaterMarkIn {
    public static void main(String[] args) {
        String pid ="de192aad";
        String wid ="185bcd6a";
        String wpid = "011119a0";
        String description ="seffswef 第一份fft水印";
        String mode = "dwt";
        String uid ="bbbbbbbb";
       WaterMarkIn.IN(uid,mode,pid, wid, description);
       WaterMarkIn.OUT(uid, mode, wpid, pid);
    }
    public static String IN(String uid,String mode,String pid, String wid, String description) {
        Map<Object, Object> PicresultMap = ImageUtil.readDBImage(pid, "picture");       
        Map<Object, Object> WaterresultMap = ImageUtil.readDBImage(wid, "watermark");
        
        System.out.println("in water mark in"+PicresultMap.get("picpath"));
        
        String ways = mode;
        String baseimgPath =(String) PicresultMap.get("picpath") ;
        String waterimgPath = (String)WaterresultMap.get("picpath");
        String savePath = ImageUtil.TEMP_PATH + File.separator + ways + File.separator;
        int height = (int) WaterresultMap.get("height");// 水印的宽高
        int width = (int) WaterresultMap.get("width");
        // 对嵌入操作
        WaterMakUtil waterMakUtil = new WaterMakUtil(ways, baseimgPath, waterimgPath, savePath, height, width);
        String waterMarkedPath = waterMakUtil.UseWaterMakein();
        System.out.println("IN "+waterMarkedPath);
        
        //保存水印后图片到数据库 获得wpid
        String wpid = ImageUtil.SaveAfterWarterMark(uid, pid, wid, description, width, height, mode, waterMarkedPath);
        return wpid;
    }
    
    public static String OUT(String uid,String mode,String wpid, String pid) {
        Map<Object, Object> PicresultMap = ImageUtil.readDBImage(pid, "picture");       
        Map<Object, Object> WateredresultMap = ImageUtil.readDBImage(wpid, "afterwatermark");      
        System.out.println("in OUT"+PicresultMap.get("picpath"));        
        System.out.println("in OUT show watered "+WateredresultMap.get("picpath"));        
        String ways = mode;
        String baseimgPath =(String) PicresultMap.get("picpath") ;
        String wateredimgPath = (String)WateredresultMap.get("picpath");
        String savePath = ImageUtil.TEMP_PATH + File.separator + ways + File.separator;
        int height = (int) WateredresultMap.get("height");// 水印的宽高
        int width = (int) WateredresultMap.get("width");
        System.out.println("in WaterMarkIn height and width"+height+"..."+width);
        // 提取
        WaterMakUtil waterMakUtil = new WaterMakUtil(ways, baseimgPath, null, savePath, height, width);
        String wmarkPath = waterMakUtil.UseWaterMakeout(wateredimgPath);      
        return wmarkPath;
    }
    
    public static String OUTAttacked(String uid,String mode,String wpid, String pid, String attackedPath) {
        Map<Object, Object> PicresultMap = ImageUtil.readDBImage(pid, "picture");       
        Map<Object, Object> WateredresultMap = ImageUtil.readDBImage(wpid, "afterwatermark");  
        System.out.println("in OUTAttacked"+PicresultMap.get("picpath"));        
        String ways = mode;
        String baseimgPath =(String) PicresultMap.get("picpath") ;
        String wateredimgPath = attackedPath;
        String savePath = ImageUtil.TEMP_PATH + File.separator + ways + File.separator;
        int height = (int) WateredresultMap.get("height");// 水印的宽高
        int width = (int) WateredresultMap.get("width");
        // 提取
        WaterMakUtil waterMakUtil = new WaterMakUtil(ways, baseimgPath, null, savePath, height, width);
        String wmarkPath = waterMakUtil.UseWaterMakeout(wateredimgPath);      
        return wmarkPath;
    }
    
    

}
