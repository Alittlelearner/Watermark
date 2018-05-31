package tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * 图像处理类.
 */

import javax.imageio.ImageIO;

public class ImageChanges {

    private static String DEFAULT_PREVFIX = "thumb_";
    private static Boolean DEFAULT_FORCE = false;// 建议该值为false

    String openUrl; // 原始图片打开路径
    String saveUrl; // 新图保存路径
    String saveName; // 新图名称
    String suffix; // 新图类型 只支持gif,jpg,png

    public ImageChanges(String openUrl, String saveUrl, String saveName, String suffix) {
        this.openUrl = openUrl;
        this.saveName = saveName;
        this.saveUrl = saveUrl;
        this.suffix = suffix;

        File file = new File(saveUrl + File.separator + saveName + suffix);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
    }

    public ImageChanges() {
    };

    /**
     * 图片缩放.
     * 
     * @param proportion
     *            缩放的比例 （倍数）
     * @throws Exception
     */
    public String zoom(double proportion) throws Exception {
        File file = new File(openUrl);
        if (!file.isFile()) {
            throw new Exception("ImageAttack>>>" + file + " 不是一个图片文件!");
        }
        BufferedImage bi = ImageIO.read(file); // 读取该图片
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(proportion, proportion), null);
        File out = new File(saveUrl, saveName + "_zoom" + "." + suffix);
        Image zoomImage = op.filter(bi, null);
        try {
            ImageIO.write((BufferedImage) zoomImage, suffix, out); // 保存图片
            return out.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 旋转
     * 
     * @param degree
     *            旋转角度
     * @throws Exception
     */
    public String spin(int degree) throws Exception {
        int swidth = 0; // 旋转后的宽度
        int sheight = 0; // 旋转后的高度
        int x; // 原点横坐标
        int y; // 原点纵坐标

        File file = new File(openUrl);
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }
        BufferedImage bi = ImageIO.read(file); // 读取该图片
        // 处理角度--确定旋转弧度
        degree = degree % 360;
        if (degree < 0)
            degree = 360 + degree;// 将角度转换到0-360度之间
        double theta = Math.toRadians(degree);// 将角度转为弧度

        // 确定旋转后的宽和高
        if (degree == 180 || degree == 0 || degree == 360) {
            swidth = bi.getWidth();
            sheight = bi.getHeight();
        } else if (degree == 90 || degree == 270) {
            sheight = bi.getWidth();
            swidth = bi.getHeight();
        } else {
            swidth = (int) (Math.sqrt(bi.getWidth() * bi.getWidth() + bi.getHeight() * bi.getHeight()));
            sheight = (int) (Math.sqrt(bi.getWidth() * bi.getWidth() + bi.getHeight() * bi.getHeight()));
        }

        x = (swidth / 2) - (bi.getWidth() / 2);// 确定原点坐标
        y = (sheight / 2) - (bi.getHeight() / 2);

        BufferedImage spinImage = new BufferedImage(swidth, sheight, bi.getType());
        // 设置图片背景颜色
        Graphics2D gs = (Graphics2D) spinImage.getGraphics();
        gs.setColor(Color.black);
        gs.fillRect(0, 0, swidth, sheight);// 以给定颜色绘制旋转后图片的背景

        AffineTransform at = new AffineTransform();
        at.rotate(theta, swidth / 2, sheight / 2);// 旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        spinImage = op.filter(bi, spinImage);
        File out = new File(saveUrl, saveName + "_spin" + "." + suffix);
        ImageIO.write(spinImage, suffix, out); // 保存图片
        System.out.println("done");
        return out.getAbsolutePath();
       

    }

    /**
     * 马赛克化.
     * 
     * @param size
     *            马赛克尺寸，即每个矩形的长宽
     * @return
     * @throws Exception
     */
    public String mosaic(int size) throws Exception {
        File file = new File(openUrl);
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }
        BufferedImage bi = ImageIO.read(file); // 读取该图片
        BufferedImage spinImage = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.TYPE_INT_RGB);
        if (bi.getWidth() < size || bi.getHeight() < size || size <= 0) { // 马赛克格尺寸太大或太小
            return null;
        }

        int xcount = 0; // 方向绘制个数
        int ycount = 0; // y方向绘制个数
        if (bi.getWidth() % size == 0) {
            xcount = bi.getWidth() / size;
        } else {
            xcount = bi.getWidth() / size + 1;
        }
        if (bi.getHeight() % size == 0) {
            ycount = bi.getHeight() / size;
        } else {
            ycount = bi.getHeight() / size + 1;
        }
        int x = 0; // 坐标
        int y = 0;
        // 绘制马赛克(绘制矩形并填充颜色)
        Graphics gs = spinImage.getGraphics();
        for (int i = 0; i < xcount; i++) {
            for (int j = 0; j < ycount; j++) {
                // 马赛克矩形格大小
                int mwidth = size;
                int mheight = size;
                if (i == xcount - 1) { // 横向最后一个比较特殊，可能不够一个size
                    mwidth = bi.getWidth() - x;
                }
                if (j == ycount - 1) { // 同理
                    mheight = bi.getHeight() - y;
                }
                // 矩形颜色取中心像素点RGB值
                int centerX = x;
                int centerY = y;
                if (mwidth % 2 == 0) {
                    centerX += mwidth / 2;
                } else {
                    centerX += (mwidth - 1) / 2;
                }
                if (mheight % 2 == 0) {
                    centerY += mheight / 2;
                } else {
                    centerY += (mheight - 1) / 2;
                }
                Color color = new Color(bi.getRGB(centerX, centerY));
                gs.setColor(color);
                gs.fillRect(x, y, mwidth, mheight);
                y = y + size;// 计算下一个矩形的y坐标
            }
            y = 0;// 还原y坐标
            x = x + size;// 计算x坐标
        }
        gs.dispose();
        File out = new File(saveUrl, saveName + "_mosaic" + "." + suffix);
        ImageIO.write(spinImage, suffix, out); // 保存图片
        System.out.println("done");
        return out.getAbsolutePath();
    }

    /**
     * 生成缩略图
     * @param imagePath
     * @param w
     * @param h
     * @param prevfix
     * @param force
     */
    public static void thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force) {
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF,
                // gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                if (imgFile.getName().indexOf(".") > -1) {
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                } // 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0) {
                    System.err.println("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return;
                }
                System.err.println("target image's size, width:{}, height:{}." + w + h);
                Image img = ImageIO.read(imgFile);
                if (!force) {
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if ((width * 1.0) / w < (height * 1.0) / h) {
                        if (width > w) {
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
                            System.err.println("change image's height, width:{}, height:{}." + w + h);
                        }
                    } else {
                        if (height > h) {
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
                            System.err.println("change image's height, width:{}, height:{}." + w + h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                String p = imgFile.getPath();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, new File(
                        p.substring(0, p.lastIndexOf(File.separator)) + File.separator + prevfix + imgFile.getName()));
                System.out.println("缩略图在原路径下生成成功");
            } catch (IOException e) {
                System.out.println("fail");
            }
        } else {
            System.out.println("the image is not exist.");
        }
    }

    /**
     * 裁剪图片  给出开始和结束这个矩阵的两个对角点的坐标
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     */
    public  void cropImage(int startX, int startY, int endX, int endY) {
        File newfile = new File(openUrl);
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(newfile);

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (startX == -1 || startX > width) {
                startX = 0;
            }
            if (startY == -1 || startY > height) {
                startY = 0;
            }
            if (endX == -1 || endX > width) {
                endX = width - 1;
            }
            if (endY == -1 || endY > height) {
                endY = height - 1;
            }
            BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
            for (int x = startX; x < endX; ++x) {
                for (int y = startY; y < endY; ++y) {
                    int rgb = bufferedImage.getRGB(x, y);
                    result.setRGB(x - startX, y - startY, rgb);
                }
            }    
            File out = new File(saveUrl, saveName + "_crop" + "." + suffix);
            ImageIO.write(result, suffix, out);    //输出裁剪图片
            System.out.println("done");
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    public String salt(int number) {
        File newfile = new File(openUrl);
        BufferedImage bufferedImage;
        int x;
        int y;
        try {
            bufferedImage = ImageIO.read(newfile);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();          
            BufferedImage result = bufferedImage;            
            for(int i = 1; i < number; i++) {
                x =new Random().nextInt(width);
                y = new Random().nextInt(height);
                result.setRGB(x, y, 0);
            }
            File out = new File(saveUrl, saveName + "_salt" + "." + suffix);
            ImageIO.write(result, suffix, out);    //输出椒盐噪声后图片
            System.out.println("done");
            return out.getAbsolutePath();
            
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
        
    }
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {  
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());  
    } 
    public String addWord(String word) {
        Font font = new Font("微软雅黑", Font.PLAIN, 30); 
        Color color=new Color(240,240,240,200);  
        try {
            // 读取原图片信息
            File srcImgFile = new File(openUrl);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(color); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            //设置水印的坐标
            int x = srcImgWidth - 2*getWatermarkLength(word, g);  
            int y = srcImgHeight - getWatermarkLength(word, g);  
            System.out.println(" x, y"+x +":" + y);
            g.drawString(word, x, y);  //画出水印
            g.dispose();  
            System.out.println("word was"+ word);
            File out = new File(saveUrl, saveName + "_word" + "." + suffix);
            ImageIO.write(bufImg, suffix, out);    //输出文字水印后图片
            System.out.println("done");
            return out.getAbsolutePath();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        ImageChanges attack = new ImageChanges("D://picture.png", "D://picture", "picture", "jpg");
        
        /*attack.zoom(0.5); //缩放为原来的多少 倍 // 测试旋转 attack.spin(90); //测试马赛克
        attack.spin(30); //测试旋转
        attack.mosaic(4);//测试马赛克
        attack.cropImage(20, 20, 200, 600); //测试裁剪
        attack.addWord("试一试文字的水印");*/
        String path = attack.salt(300);
        System.out.println(path);
    }

}
