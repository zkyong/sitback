package com.zkyong.demo.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zkyong.demo.string.StringUtil;

/**
 * 文件操作工具类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:18:20
 */
public class FileUtils {

    private static final Logger LOGGER           = LoggerFactory.getLogger(FileUtils.class);
    /**
     * 回车符的byte值
     */
    private static final byte   BYTE_CR          = 13;

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String PATH_SPIT_REGEX  = "\\\\|/";

    private static final String PATH_CUREENT     = ".";

    private static final String PATH_PARENT      = "..";

    public static final String  ADDED            = "added";

    public static final String  REMOVED          = "removed";

    public static final String  DIFF             = "diff";

    /**
     * 创建文件
     * 
     * @param path 文件路径
     * @return 成功则返回true
     */
    public static boolean createFile(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            LOGGER.error("创建文件时异常", e);
            return false;
        }
        return true;
    }

    /**
     * 写入文件
     * 
     * @param path 文件路径
     * @param content 文件内容
     * @return 成功则返回true
     */
    public static boolean writeFile(String path, String content) {
        File file = new File(path);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            LOGGER.error("写入文件时异常", e);
            return false;
        }
        return true;
    }

    /**
     * 为文件赋权限
     * 
     * @param filePath
     * @return 成功则返回true
     */
    public static boolean addPermission(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        file.canWrite();
        Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        try {
            Path path = Paths.get(file.getAbsolutePath());
            Files.setPosixFilePermissions(path, perms);
        } catch (Exception e) {
            LOGGER.error("为文件赋权限时异常", e);
            return false;
        }
        return true;
    }

    /**
     * 读取文本文件内容
     * 
     * @param filePath 文件完整路径
     * @return 返回文本文件的内容
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        return readFile(filePath, DEFAULT_ENCODING);
    }

    /**
     * 读取文本文件内容
     * 
     * @param filePath 文件完整路径
     * @param encoding 文本编码方式
     * @return 返回文本文件的内容
     */
    public static String readFile(String filePath, String encoding) throws IOException {
        if (StringUtil.isEmpty(encoding)) {
            encoding = DEFAULT_ENCODING;
        }
        StringBuffer result = new StringBuffer("");
        FileInputStream fis = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding.trim()));
        String data = "";
        while ((data = br.readLine()) != null) {
            result.append(data + " ");
        }
        br.close();
        fis.close();
        return result.toString();
    }

    /**
     * 拷贝文件
     * 
     * @param oldPath 原路径
     * @param newPath 新路径
     */
    public static void copyFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(oldFile);
            out = new FileOutputStream(newFile);
            byte[] buffer = new byte[in.available()];
            int len = in.read(buffer);
            if (len != -1) {
                out.write(buffer);
            }
        } catch (IOException e) {
            LOGGER.error("执行文件拷贝时异常", e);
        } finally {
            try {
                if (null != out) {
                    out.close();
                    in.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭输入输出流异常", e);
            }
        }
    }

    /**
     * 拷贝文件夹
     * 
     * @param oldPath 原路径
     * @param newPath 新路径
     */
    public static void copyFolder(String oldPath, String newPath) {
        File oldFolder = new File(oldPath);
        String[] subs = oldFolder.list();

        if (!new File(newPath).exists()) {
            new File(newPath).mkdirs();
        }

        String oldSubPath = null;
        for (String sub : subs) {
            oldSubPath = combinePath(oldPath, sub);
            if (new File(oldSubPath).isDirectory()) {
                copyFolder(oldSubPath, combinePath(newPath, sub));
            } else if (new File(oldSubPath).isFile()) {
                copyFile(oldSubPath, combinePath(newPath, sub));
            }
        }
    }

    /**
     * 删除指定文件
     * 
     * @param filePath 文件路径
     */
    public static void delFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除指定文件夹
     * 
     * @param path 文件夹路径
     */
    public static void delFolder(String folderPath) {
        delAllFile(folderPath);
        File folder = new File(folderPath);
        folder.delete();
    }

    /**
     * 删除指定文件夹下所有文件
     * 
     * @param folderPath 文件夹路径
     */
    public static void delAllFile(String folderPath) {
        if (StringUtil.isEmpty(folderPath)) {
            return;
        }
        File folder = new File(folderPath);
        if (!folder.exists()) {
            return;
        }
        if (!folder.isDirectory()) {
            return;
        }
        String[] list = folder.list();
        String subPath = null;
        File sub = null;
        for (String subName : list) {
            subPath = combinePath(folderPath, subName);
            sub = new File(subPath);
            if (sub.isFile()) {
                sub.delete();
            }
            if (sub.isDirectory()) {
                delAllFile(subPath);
                delFolder(subPath);
            }
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 移动文件
     * 
     * @param oldPath 原路径
     * @param newPath 新路径
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    /**
     * 移动文件夹
     * 
     * @param oldPath 原路径
     * @param newPath 新路径
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    /**
     * 比较文件是否相同(MD5值)
     * 
     * @param source 原文件
     * @param target 目标文件
     * @return boolean
     * @throws Exception 
     */
    public static boolean compareFile(String source, String target) throws Exception {
        String sourceMD5 = getFileMD5(new File(source));
        String targetMD5 = getFileMD5(new File(target));
        if (null == sourceMD5 && null == targetMD5) {
            return true;
        }
        if (null == sourceMD5 && null != targetMD5 || null != sourceMD5 && null == targetMD5) {
            return false;
        }
        return sourceMD5.equals(targetMD5);
    }

    /**
     * 比较文件夹下文件是否相同
     * 
     * @param source 原文件夹(备份文件夹)
     * @param target 目标文件夹
     * @return Map<String, List<String>> 返回最终比较结果的集合
     * @throws Exception 
     */
    public static Map<String, List<String>> compareFolder(String source, String target,
                                                          String basePath) throws Exception {
        // 获取绝对路径
        String sourcePath = new File(source).getAbsolutePath();
        String targetPath = new File(target).getAbsolutePath();

        // 增加
        List<String> addList = new ArrayList<>();
        // 删除
        List<String> removeList = new ArrayList<>();
        // 变更
        List<String> diffList = new ArrayList<>();

        Map<String, String> sourceMD5Map = getFolderMD5(new File(sourcePath));
        Map<String, String> targetMD5Map = getFolderMD5(new File(targetPath));
        for (Entry<String, String> entry : sourceMD5Map.entrySet()) {
            String sourceKey = entry.getKey();
            String sourceValue = entry.getValue();
            String targetKey = sourceKey.replace(sourcePath, targetPath);
            String targetValue = targetMD5Map.remove(targetKey);
            // 项目中有，自动生成时没有
            if (sourceValue != null && targetValue == null) {
                addList.add(removeHeadPath(targetKey, basePath));
            }
            // 项目中没有，自动生成时有
            if (sourceValue == null && targetValue != null) {
                removeList.add(removeHeadPath(targetKey, basePath));
            }
            // 项目中有，自动生成时也有
            if (sourceValue != null && targetValue != null) {
                if (!sourceValue.equals(targetValue)) {
                    diffList.add(removeHeadPath(targetKey, basePath));
                }
            }
        }
        for (String targetKey : targetMD5Map.keySet()) {
            removeList.add(removeHeadPath(targetKey, basePath));
        }

        Map<String, List<String>> compareResultMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(addList)) {
            compareResultMap.put(ADDED, addList);
        }
        if (!CollectionUtils.isEmpty(removeList)) {
            compareResultMap.put(REMOVED, removeList);
        }
        if (!CollectionUtils.isEmpty(diffList)) {
            compareResultMap.put(DIFF, diffList);
        }
        return compareResultMap;
    }

    /**
     * 获取文件MD5值
     * 
     * @param file 原文件
     * @return String MD5字符串
     * @throws Exception 
     */
    public static String getFileMD5(File file) throws Exception {
        if (null == file || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream fis = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte buffer[] = new byte[fis.available()];
            int len = fis.read(buffer);
            if (len != -1) {
                // 移除回车符的byte值
                digest.update(removeByte(buffer, BYTE_CR));
            }
        } catch (Exception e) {
            LOGGER.error("获取文件MD5值时异常", e);
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭文件输入流异常", e);
            }
        }
        return String.valueOf(Hex.encodeHex(digest.digest()));
    }

    /**
     * 移除byte数组中的指定值
     * 
     * @param bytes 待处理byte数组
     * @param b 指定的byte值
     * @return byte[] 删除指定值后的新byte数组
     */
    private static byte[] removeByte(byte[] bytes, byte b) {
        int index = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != b) {
                bytes[index] = bytes[i];
                index++;
            }
        }
        return Arrays.copyOf(bytes, index);
    }

    /**
     * 递归获取文件夹中所有文件的MD5值
     * 
     * @param file 文件夹
     * @return Map<String, String>
     * @throws Exception 
     */

    public static Map<String, String> getFolderMD5(File file) throws Exception {
        if (null == file || !file.isDirectory()) {
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();
        String md5 = null;
        File subs[] = file.listFiles();
        for (File sub : subs) {
            if (sub.isDirectory()) {
                map.putAll(getFolderMD5(sub));
            } else {
                md5 = getFileMD5(sub);
                if (md5 != null) {
                    map.put(sub.getPath(), md5);
                }
            }
        }
        return map;
    }

    /**
     * 从指定路径查找指定名称的文件
     * 
     * @param folderPath 待查找文件夹路径
     * @param name 目标文件名称
     * @param list 返回满足要求的路径集合
     */
    public static void searchFile(String folderPath, String name, List<String> list) {
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return;
        }

        File[] subs = folder.listFiles();
        if (subs == null || subs.length == 0) {
            return;
        }
        for (File sub : subs) {
            if (sub.isFile() && sub.getName().equalsIgnoreCase(name)) {
                try {
                    list.add(sub.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sub.isDirectory()) {
                searchFile(sub.getPath(), name, list);
            }
        }
    }

    /**
     * 解析文件(夹)路径，去除"../"、"./"等
     * 
     * @param current 当前路径
     * @param target 待解析路径
     * @return String
     */
    public static String parsePath(String current, String target) {
        current = formatPath(current);
        target = formatPath(target);
        if (StringUtil.isEmpty(target)) {
            return current;
        }
        StringBuffer sb = new StringBuffer();
        if (target.startsWith(File.separator)) {
            sb.append(File.separator);
        } else {
            target = combinePath(current, target);
        }

        String[] strs = target.split(PATH_SPIT_REGEX);
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            switch (str) {
                case PATH_CUREENT:
                    break;
                case PATH_PARENT:
                    sb.delete(sb.lastIndexOf(File.separator), sb.length());
                    break;
                default:
                    sb.append(File.separator);
                    sb.append(str);
                    break;
            }
        }
        return sb.toString().substring(1);
    }

    /**
     * 拼接文件(夹)路径
     * 
     * @param source 原路径
     * @param target 子路径
     * @return String
     */
    public static String combinePath(String source, String target) {
        source = formatPath(source);
        target = formatPath(target);
        if (target.startsWith(PATH_CUREENT)) {
            target = target.substring(2);
        }
        if (target.startsWith(File.separator)) {
            target = target.substring(1);
        }
        if (source.endsWith(File.separator)) {
            return source + target;
        } else {
            return source + File.separator + target;
        }
    }

    /**
     * 去除头部的路径
     * 
     * @param source 原路径
     * @param target 待去除路径
     * @return String
     */
    public static String removeHeadPath(String source, String headPath) {
        source = formatPath(source);
        headPath = formatPath(headPath);
        //		if (headPath.endsWith(File.separator)) {
        //			headPath = headPath.substring(0, headPath.length() -1 );
        //		}
        if (!headPath.endsWith(File.separator)) {
            headPath = headPath.concat(File.separator);
        }
        return source.substring(source.indexOf(headPath) + headPath.length());
    }

    /**
     * 获取文件所在文件夹路径
     * 
     * @param filePath 文件路径
     */
    public static String getParentPath(String filePath) {
        return new File(filePath).getParent();
    }

    /**
     * 获取文件上一级目录名称
     * 
     * @param filePath 文件路径
     */
    public static String getParentName(String filePath) {
        return new File(filePath).getParentFile().getName();
    }

    /**
     * 读取配置文件
     * 
     * @param path 配置文件绝对路径
     * @return Properties
     */
    public static Properties loadProperties(String path) {
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

    /**
     * 格式化路径，统一路径分隔符
     * 
     * @param source 待处理路径
     * @return 返回处理后的路径
     */
    private static String formatPath(String source) {
        return source.replaceAll(PATH_SPIT_REGEX, Matcher.quoteReplacement(File.separator));
    }
}