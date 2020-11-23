package com.fosun.storekeeper.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fosun.storekeeper.common.Constants;
import com.fosun.storekeeper.service.impl.SkServiceImpl;

/**
 * FastDFS Java客户端工具类
 * @author hengjb
 * @version 1.0.0
 * @Project storekeeper
 */

public class FileManager {

    private static Logger logger = LoggerFactory.getLogger(FileManager.class);

    static {
        try {
            logger.info("读取fdfs_client.conf配置文件信息，初始化FDFSClient");
            if (StringUtils.endsWith(SkServiceImpl.CONFIG, Constants.CONFIG)) {
                String fdfsClientConfigFilePath = SkServiceImpl.CONF;
                ClientGlobal.init(fdfsClientConfigFilePath);
            } else {
                String fdfsClientConfigFilePath = Constants.FDFS_CLIENT_CONF_FILE_PATH;
                ClientGlobal.init(fdfsClientConfigFilePath);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件上传
     * @param file
     * @return fileAbsolutePath
     */
    public static String[] upload(FastDFSFile file, NameValuePair[] valuePairs) throws IOException, MyException {
        String[] fileid = null;
        try {
            TrackerClient tc = new TrackerClient();
            TrackerServer ts = tc.getConnection();
            if (ts == null) {
                logger.info("getConnection return null");
                throw new RuntimeException("can not connect to track server");
            }
            StorageServer ss = tc.getStoreStorage(ts);
            if (ss == null) {
                logger.info("getStoreStorage return null");
            }
            StorageClient sc = new StorageClient(ts, ss);

            fileid = sc.upload_file(file.getContent(), file.getExt(), valuePairs);

        } catch (Exception ex) {
            logger.error("FileManager uploading is error", ex);
        }
        return fileid;
    }

    /**
     * 获取文件字节流
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws IOException
     * @throws MyException
     * @Description: 
     * @author hengjb
     * @create date 2019年8月21日下午1:53:51
     */
    public static byte[] getByte(String groupName, String remoteFileName) throws IOException, MyException {
        byte[] content = null;
        TrackerClient tc = new TrackerClient();
        TrackerServer ts = tc.getConnection();
        StorageServer ss = tc.getStoreStorage(ts);
        StorageClient sc = new StorageClient(ts, ss);
        content = sc.download_file(groupName, remoteFileName);
        return content;
    }

    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws IOException
     * @throws MyException
     * @Description: 
     * @author hengjb
     * @create date 2019年8月21日下午1:54:10
     */
    public static int delete(String groupName, String remoteFileName) throws IOException, MyException {
        TrackerClient tc = new TrackerClient();
        TrackerServer ts = tc.getConnection();
        StorageServer ss = tc.getStoreStorage(ts);
        StorageClient sc = new StorageClient(ts, ss);
        return sc.delete_file(groupName, remoteFileName);
    }
}