package com.husen.utils.sftp;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by HuSen on 2018/11/7 13:20.
 */
@SuppressWarnings("ALL")
public class SFTPClient {
    private ChannelSftp sftp;
    private Session session;

    /** SFTP登录用户名 */
    private String username;
    /** SFTP登录密码 */
    private String password;
    /** 私钥 */
    private String privateKey;
    /** IP地址 */
    private String host;
    /** 端口 */
    private int port;

    /**
     * 构造基于密码的sftp对象
     * @param username 用户名
     * @param password 密码
     * @param host 地址
     * @param port 端口号
     */
    public SFTPClient(String username, String password, String host, int port) {
        super();
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 构造基于密钥认证的sftp对象
     * @param username 用户名
     * @param privateKey 密钥
     * @param port 端口
     * @param host 地址
     */
    public SFTPClient(String username, String privateKey, int port, String host) {
        super();
        this.username = username;
        this.privateKey = privateKey;
        this.host = host;
        this.port = port;
    }

    public SFTPClient() {
        super();
    }


    /**
     * 连接sftp服务器
     */
    public void login() {
        try {
            JSch jSch = new JSch();
            if(StringUtils.isNotBlank(privateKey)) {
                jSch.addIdentity(privateKey);
            }
            session = jSch.getSession(username, host, port);
            if(StringUtils.isNotBlank(password)) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void logout() {
        if(null != sftp) {
            if(sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if(null != session) {
            if(session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param directory  上传到该目录
     * @param sftpFileName  sftp端文件名
     * @param in   输入流
     */
    public void upload(String directory, String sftpFileName, InputStream in) throws SftpException {
        try {
            sftp.cd(directory);
        }catch (Exception e) {
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(in, sftpFileName);
    }

    /**
     * 下载文件。
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException {
        if(StringUtils.isNotBlank(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if(StringUtils.isNotBlank(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);
        return IOUtils.toByteArray(is);
    }

    /**
     * 删除文件
     * @param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * 列出目录下的文件
     * @param directory 要列出的目录
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

//    public static void main(String[] args) throws FileNotFoundException, SftpException {
//        SFTPClient client = new SFTPClient("mysftp", "521428Slyt", "118.24.38.46", 22);
//        client.login();
//        上传
//        File file = new File("C:\\Users\\HS\\Desktop\\桌面壁纸\\timg (2).jpg");
//        InputStream is = new FileInputStream(file);
//        client.upload("upload", "test.jpg", is);
//        下载
//        client.download("upload", "test.jpg", "C:\\Users\\HS\\Desktop\\桌面壁纸\\timg (21).jpg");
//        client.logout();
//    }
}
