package com.lvmama.tnt.biz.voucher;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static void createDestDirectoryIfNecessary(String destParam) {
        File destDir;
        if (destParam.endsWith(File.separator)) {
            destDir = new File(destParam);
        } else {
            destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
        }
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    /**
     * 将byte二进制文件转成文件
     * @param bfile 二进制文件
     * @param filePath  路径
     * @param fileName 新的文件名
     * @return
     */
    public static File getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    public static ZipFile zip(String src, String dest) {
        File srcFile = new File(src);
        ZipFile zipFile;
        dest = buildZipFilePath(srcFile, dest);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);           // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别
        try {
            File tmp = new File(dest);
            tmp.delete();
            zipFile = new ZipFile(tmp);
            logger.info("srcFile是不是文件夹"+srcFile.isDirectory());
            if (srcFile.isDirectory()) {
                zipFile.addFolder(srcFile, parameters);
            } else {
                zipFile.addFile(srcFile, parameters);
            }
            return zipFile;
        } catch (Exception e) {
            logger.error("压缩文件异常 src" + src,e);
        }
        return null;
    }

    public static String buildZipFilePath(File srcFile,String destParam) {
        if (destParam == null || "".equals(destParam)) {
            if (srcFile.isDirectory()) {
                destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
            } else {
                destParam = srcFile.getParent() + File.separator + "virtual_code" + ".zip";
            }
        } else {
            //压缩路径不为空
            createDestDirectoryIfNecessary(destParam);  //在指定路径不存在的情况下将其创建出来
            if (destParam.endsWith(File.separator)) {
                String fileName;
                if (srcFile.isDirectory()) {
                    fileName = srcFile.getName();
                } else {
                    fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                }
                destParam += fileName + ".zip";
            }
        }
        return destParam;
    }

    public static String createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            logger.info("创建目录失败，目标目录已存在！");
            deleat(dir);
        }
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            logger.info("创建目录成功！" + destDirName);
            return destDirName;
        } else {
            logger.info("创建目录失败！");
            return "创建目录失败！";
        }
    }

    public static void deleat(File file) {
        logger.info("文件存在："+file.exists()+"是文件："+file.isFile()+"是文件夹："+file.isDirectory());
        //检查文件是否存在，如果不存在直接返回，不进行下面的操作
        if(!file.exists()){
            return;
        }
        //如果是文件删除，就删除文件，然后返回，不进行下面的操作
        if(file.isFile()){
            file.delete();
            return;
        }
        //是文件夹
        if(file.isDirectory()){
            //循环所有文件夹里面的内容并删除
            File[] files=file.listFiles();
            if (files!=null) {
                for (File f : files) {
                    logger.info("开始删除文件夹下的文件");
                    //使用迭代，调用自己

                    deleat(f);
                }
            }
            //删除自己
            file.delete();
        }
    }

    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            buffer = org.apache.commons.io.FileUtils.readFileToByteArray(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
