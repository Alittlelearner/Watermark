package tool;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import com.mathworks.toolbox.javabuilder.MWException;

import WaterMake.DCT;
import WaterMake.DWT;
import WaterMake.FFT;
import WaterMake.ATTACK;

/*
 * in(baseimg,waterimg,savepath)
 * out(height,width,baseimg,wateredimg,savepath)
 */
public class WaterMakUtil {
    String ways;
    String baseimgPath;
    String waterimgPath;
    String savePath;
    int height;
    int width;

    public WaterMakUtil(String ways, String baseimgPath, String waterimgPath, String savePath, int height, int width) {
        this.ways = ways;
        this.baseimgPath = baseimgPath;
        this.waterimgPath = waterimgPath;
        this.savePath = savePath;
        this.height = height;
        this.width = width;
        if (savePath != null && !new File(savePath).exists()) {
            new File(savePath).mkdirs();
        }
    }

    public WaterMakUtil() {
    };

    public static void main(String[] args) {

        String picture = "picture";
        String watermark = "watermark";
        String picname = "index.jpg";
        String watermarkname = "watersm.jpg";
        String ways = "dct";
        String baseimgPath = ImageUtil.TEMP_PATH + File.separator + picture + File.separator + picname;
        String waterimgPath = ImageUtil.TEMP_PATH + File.separator + watermark + File.separator + watermarkname;
        String savePath = ImageUtil.TEMP_PATH + File.separator + ways + File.separator;
        int height = 60;// 用数据库保存 这些内容 嵌入水印时保存这些参数 尤其是宽高，再提取水印时读取宽高等信息
        int width = 221;
       /* WaterMakUtil waterMakUtil = new WaterMakUtil(ways, baseimgPath, waterimgPath, savePath, height, width);
        String waterMarkedPath = waterMakUtil.UseWaterMakein();
        String GetWaterPath = waterMakUtil.UseWaterMakeout(waterMarkedPath); // 提取的水印固定名称为wmark.png
        System.out.println(GetWaterPath);*/
        
        String wayforattack = "afterwatermark";
        String pathforattact = ImageUtil.TEMP_PATH + File.separator + wayforattack + File.separator;
        WaterMakUtil waterMakUtil = new WaterMakUtil(wayforattack, null, null, pathforattact, 0, 0);
        String id ="aed50f32";
        Map<Object, Object> wateredsultMap = ImageUtil.readDBImage(id, wayforattack);
        String picpath = (String) wateredsultMap.get("picpath");
        
        String mode = "mosaic";//{"whitenoise" ,"gaussian" ,"cut" ,"spin" ,"mosaic"}
        String attackedPath =waterMakUtil.UseWaterAttack(mode, picpath);
        System.out.println(attackedPath);
       
    }

    public String UseWaterMakein() {
        Class<WaterMakUtil> clazz = WaterMakUtil.class;
        Object[] inparams = { baseimgPath, waterimgPath, savePath };// 参数：基底图位置，水印图位置，存储位置
        Method method;
        try {
            String function = ways + "_in";
            // 看看是哪个在运行
            System.out.println(function);
            method = clazz.getMethod(function, Object[].class);
            Object[] returns = (Object[]) method.invoke(clazz.newInstance(), new Object[] { inparams });
            System.out.println("watermakein success " + ImageUtil.TEMP_PATH + File.separator + ways + File.separator
                    + WaterMakUtil.wateredpath(ways, baseimgPath));
            height = Integer.parseInt(returns[0].toString());
            width = Integer.parseInt(returns[1].toString());
            String waterMarkedPath = ImageUtil.TEMP_PATH + File.separator + ways + File.separator
                    + WaterMakUtil.wateredpath(ways, baseimgPath);
            return waterMarkedPath;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String UseWaterMakeout(String waterMarkedPath) {
        Class<WaterMakUtil> clazz = WaterMakUtil.class;
        System.out.println("in userWatermarkOut H + w "+height+":"+width);
        Object[] outparams = { height, width, baseimgPath, waterMarkedPath, savePath };
        Method method = null;
        String function = ways + "_out";
        // 看看是哪个在运行
        System.out.println("function : " + function);
        try {
            method = clazz.getMethod(function, Object[].class);
            method.invoke(clazz.newInstance(), new Object[] { outparams });
            System.out.println("watermakeout success " + savePath);
            return savePath + "wmark.png";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String UseWaterAttack(String mode, String imagePath) {
        System.out.println("kkkkkkkkkk"+mode);
        String[] ways = new String[] {"whitenoise", "gaussian", "cut", "mosaic"};
        int attackmode = 0;
        for (int i = 0; i < ways.length; i++) {
            if(ways[i].equals(mode)) {
                attackmode = i+1;
            }
        }
        Object[] inparams = {imagePath, attackmode,savePath };// 参数：基底图位置，水印图位置，存储位置
        Object returns = WaterMakUtil.ImageAttack(inparams);
        String[] pathparts = Mytool.fileparts(imagePath);
        String attackedPath = savePath + pathparts[1] + "_" + mode + pathparts[2];
        System.out.println("end attack");
        return attackedPath;
        
       
    }

    public static String wateredpath(String ways, String baseimgPath) {
        File tempFile = new File(baseimgPath);
        String fileName = tempFile.getName();
        fileName = fileName.replace(Mytool.getExtName(fileName, '.'), "");
        return fileName + "_" + ways + ".png";
    }

    public static Object[] fft_in(Object[] inparams) {

        try {
            FFT fft = new FFT();
            Object[] returns = fft.fft2in(2, inparams);
            return returns;
        } catch (MWException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fft_out(Object[] outparams) {

        try {
            FFT fft = new FFT();
            Object[] returns = fft.fft2out(1, outparams);
        } catch (MWException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[] dct_in(Object[] inparams) {

        try {
            DCT dct = new DCT();
            Object[] returns = dct.dct2in(2, inparams);
            return returns;
        } catch (MWException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dct_out(Object[] outparams) {

        try {
            DCT dct = new DCT();
            Object[] returns = dct.dct2out(1, outparams);
        } catch (MWException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[] dwt_in(Object[] inparams) {

        try {
            DWT dwt = new DWT();
            Object[] returns = dwt.dwt2in(2, inparams);
            return returns;
        } catch (MWException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dwt_out(Object[] outparams) {

        try {
            DWT dwt = new DWT();
            Object[] returns = dwt.dwt2out(1, outparams);
        } catch (MWException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Object ImageAttack(Object[] inparams) {
        try {
            ATTACK attack = new ATTACK();
            Object returns = attack.attack(1,inparams);
        } catch (MWException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
