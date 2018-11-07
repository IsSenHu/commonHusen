package com.husen.utils.sftp;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HuSen on 2018/11/7 15:10.
 */
@SuppressWarnings("ALL")
public class SFTPClientFactory {

    private static final Map<Integer, SFTPClient> SFTP_CLIENT_MAP = new ConcurrentHashMap<>(1);

    public static SFTPClient of(String username, String password, String host, int port) {
        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(host)) {
            int key = (username + password + host + (long) port).hashCode();
            SFTPClient client = SFTP_CLIENT_MAP.get(key);
            if(null == client) {
                synchronized (SFTPClientFactory.class) {
                    if(null == client) {
                        client = new SFTPClient(username, password, host, port);
                        SFTP_CLIENT_MAP.put(key, client);
                    }
                }
            }
            return client;
        }else {
            throw new IllegalArgumentException("参数错误");
        }
    }

    public static SFTPClient of(String username, String privateKey, int port, String host) {
        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(privateKey) && StringUtils.isNotBlank(host)) {
            int key = (username + privateKey + host + (long)port).hashCode();
            SFTPClient client = SFTP_CLIENT_MAP.get(key);
            if(null == client) {
                synchronized (SFTPClientFactory.class) {
                    if(null == client) {
                        client = new SFTPClient(username, privateKey, port, host);
                        SFTP_CLIENT_MAP.put(key, client);
                    }
                }
            }
            return client;
        }else {
            throw new IllegalArgumentException("参数错误");
        }
    }
}
