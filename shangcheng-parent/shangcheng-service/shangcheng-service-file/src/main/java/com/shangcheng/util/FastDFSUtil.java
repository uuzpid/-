package com.shangcheng.util;

import com.shangcheng.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 实现FastDFS文件管理
 *  文件上传
 *  文件删除
 *  文件下载
 *  文件信息获取
 *  Storage信息获取
 *  Tracker信息获取
 */
public class FastDFSUtil {
    /**
     * 加载Tracker连接信息
     * 加载配置文件，文件中有Tracker的端口，可以建议Tracker连接
     */
    static {
        //String filename="D:\\Project\\shangcheng\\shangcheng-parent" +
                //"\\shangcheng-service\\shangcheng-service-file\\src\\main\\resources\\fdfs_client.conf";
        //查找classpath下的文件路径可以用ClassPathResource(文件名).getPath()
        String fileName = new ClassPathResource("fdfs_client.conf").getPath();
        try {
            ClientGlobal.init(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @return*/
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        //创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer的链接信息可以获取到Storage的链接信息,
        // 创建StorageClient对象存储Storage的链接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);

        /**
         * // 通过StorageClient访问Storage,实现文件上传,并且获取文件上传后的存储信息
         *  1.上传文件的字节数组
         *  2.文件的扩展名 jpg
         *  3.附加参数 比如：拍摄地址：北京
         */
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("拍摄地址",fastDFSFile.getAuthor());
        /**
         * upload_file()方法返回两个参数
         * uploads[0]:文件上传所存储的Storage的组名字 group1
         * uploads[1]:文件存储到Storage上的文件名字 例如M00/22/44/xxx.txt
         *
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return uploads;
    }

    /**
     * 获取文件信息
     * @param groupName :文件所在组名
     * @param remoteFileName :文件的存储路径名字 注意开头是没有斜杠的 例如：M00/00/00/wKhYg18xaBOAXR_aAAAuxIq72uo523.txt
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        //先创建TrackerClient对象，通过该对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer获取Storage信息，创建StorageClient对象存储Storage信息
        StorageClient storageClient = new StorageClient(trackerServer,null);
        //获取文件信息
        return storageClient.get_file_info(groupName,remoteFileName);
    }

    /**
     * 文件下载
     * @param groupName :文件所在组名
     * @param remoteFileName :文件的存储路径名字 注意开头是没有斜杠的 例如：M00/00/00/wKhYg18xaBOAXR_aAAAuxIq72uo523.txt
     */
    public static InputStream downFile(String groupName, String remoteFileName) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //文件下载
        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        //将字节数组转成字节输入流
        return new ByteArrayInputStream(buffer);
    }

    /**
     * 删除文件
     * @param groupName :文件所在组名
     * @param remoteFileName :文件的存储路径名字 注意开头是没有斜杠的 例如：M00/00/00/wKhYg18xaBOAXR_aAAAuxIq72uo523.txt
     * @return
     */
    public static int deleteFile(String groupName, String remoteFileName) throws Exception{
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);

        return storageClient.delete_file(groupName,remoteFileName);//0表示成功
    }

    public static void main(String[] args) throws Exception {
        //FileInfo fileInfo = getFile("group1", "M00/00/00/wKhYg18xaBOAXR_aAAAuxIq72uo523.txt");
        //System.out.println(fileInfo.getSourceIpAddr());
        //System.out.println(fileInfo.getFileSize());

        //文件下载
        //InputStream inputStream = downFile("group1", "M00/00/00/wKhYg18xaBOAXR_aAAAuxIq72uo523.txt");
        //将文件写入到本地磁盘
        //FileOutputStream fileOutputStream = new FileOutputStream("D:/1.txt");//定义一个换成区
        //byte[] buffer = new byte[1024];
        //while (inputStream.read(buffer)!=-1){
        //    fileOutputStream.write(buffer);
        //}
        //fileOutputStream.flush();
        //fileOutputStream.close();
        //inputStream.close();

        //文件删除
        System.out.println(deleteFile("group1", "M00/00/00/wKhYg18xaBOAXR_aAAAuxIq72uo523.txt"));
    }
}
