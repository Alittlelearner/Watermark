package tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

public class Mytool {

    /**
     * 根据传递的split截取后面的内容
     * 
     * @param s
     * @param split
     * @return
     */
    public static String getExtName(String s, char split) {
        int i = s.indexOf(split);
        int leg = s.length();
        return (i > 0 ? (i + 1) == leg ? " " : s.substring(i, s.length()) : " ");
        /*
         * i>0为假 输出最后一个表达式“ ” i>0为真 判断(i + 1) == leg 为真输出“ ”/为假输出 s.substring(i,
         * s.length())
         */
    }

    public static String[] fileparts(String path) {
        String[] returns = new String[3];
        returns[2] = Mytool.getExtName(path, '.');
        returns[0] = path.replaceFirst(new File(path).getName(), "");
        returns[1] = Mytool.getFileNameByPath(path).replaceFirst(returns[2], "");
        return returns;
    }

    public static String getFileNameByPath(String path) {
        int beginIndex = path.lastIndexOf(File.separator);
        if (beginIndex < 0) {
            return path;
        }
        return path.substring(beginIndex + 1);
    }

    public static int ReturnRandom(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }

    /**
     * 将后一个参数添加到前面数组的末尾
     * 
     * @param arr1
     * @param ele
     * @return
     */
    public static Object[] insertElement(Object[] arr1, Object ele) {
        Object[] source = new Object[arr1.length + 1];

        if (arr1.length == 0) {
            source[source.length - 1] = ele;
        } else {
            System.arraycopy(arr1, 0, source, 0, arr1.length);
            source[source.length - 1] = ele;
        }

        return source;
    }

    /**
     * 获得4个长度的十六进制的UUID
     * 
     * @return UUID
     */
    public static String get4UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[1];
    }

    /**
     * 获得8个长度的十六进制的UUID
     * 
     * @return UUID
     */
    public static String get8UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0];
    }
    public static byte[] InputStreamToByte(InputStream inputStream) {
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
            int ch;  
            while ((ch = inputStream.read()) != -1) {  
              bytestream.write(ch);  
            }  
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close(); 
            inputStream.close();  
            return imgdata;
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        return null;  
        
    }

    /**
     * 获得12个长度的十六进制的UUID
     * 
     * @return UUID
     */
    public static String get12UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1];
    }

    /**
     * 获得16个长度的十六进制的UUID
     * 
     * @return UUID
     */
    public static String get16UUID() {

        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2];
    }

    /**
     * 获得20个长度的十六进制的UUID
     * 
     * @return UUID
     */
    public static String get20UUID() {

        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3];
    }

    /**
     * 获得24个长度的十六进制的UUID
     * 
     * @return UUID
     */
    public static String get24UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[4];
    }

    /**
     * 获得32个长度的十六进制的UUID
     * 
     * @return UUID
     */
    public static String get32UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3] + idd[4];
    }

    /**
     * 拼接在网页上的src /temp 之后的内容
     * 
     * @param path
     * @return
     */
    public static String getSrcPath(String path) {
        int intIndex = path.indexOf("\\temp");
        return path.substring(intIndex + "\\temp".length());
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\L\\workspace\\Watermark\\temp\\afterwatermark\\index_dwt.png";
        String[] pathparts = Mytool.fileparts(path);
        for (int i = 0; i < pathparts.length; i++) {
            System.out.println(pathparts[i]);

        }
    }
    /**
     * 删除文件夹下的所有文件
     * @param path
     * @return
     */
    public static  boolean delAllFile(String path) {  
        boolean flag = false;  
        File file = new File(path);  
        if (!file.exists()) {  
            return flag;  
        }  
        if (!file.isDirectory()) {  
            return flag;  
        }  
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
            if (path.endsWith(File.separator)) {  
                temp = new File(path + tempList[i]);  
            } else {  
                temp = new File(path + File.separator + tempList[i]);  
            }  
            if (temp.isFile()) {  
                temp.delete();  
            }  
            if (temp.isDirectory()) {  
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件  
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹  
                flag = true;  
            }  
        }  
        return flag;  
    }  
    /*** 
     * 删除文件夹 
     *  
     * @param folderPath文件夹完整绝对路径 
     */  
    public  static void delFolder(String folderPath) {  
        try {  
            delAllFile(folderPath); // 删除完里面所有内容  
            String filePath = folderPath;  
            filePath = filePath.toString();  
            java.io.File myFilePath = new java.io.File(filePath);  
            myFilePath.delete(); // 删除空文件夹  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
