package com.mem.vo.common.util;

import com.google.gson.Gson;
import com.mem.vo.business.base.dao.InternetResourcesDao;
import com.mem.vo.business.base.model.po.InternetResources;
import com.mem.vo.business.base.service.InternetResourcesService;
import com.mem.vo.common.exception.BizException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author lvxiao
 * @date 2019/6/17
 */
@Component
@Slf4j
public class FileUtil {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.domainOfBucket}")
    private String domainOfBucket;

    private static UploadManager uploadManager = new UploadManager(new Configuration(Zone.zone1()));

    private Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Resource
    private InternetResourcesDao internetResourcesDao;
    /**
     * 上传本地文件
     *
     * @param localFilePath 本地文件完整路径
     * @param key           文件云端存储的名称
     * @param override      是否覆盖同名同位置文件
     * @return
     */
    public boolean upload(String localFilePath, String key, boolean override) {
        //...生成上传凭证，然后准备上传
        Auth auth = getAuth();
        String upToken;
        if (override) {
            upToken = auth.uploadToken(bucket, key);//覆盖上传凭证
        } else {
            upToken = auth.uploadToken(bucket);
        }
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件{},上传成功", putRet.key);
            return true;
        } catch (QiniuException ex) {
            Response r = ex.response;
            throw new BizException("上传文件异常，异常信息：" + r.toString());
        }
    }

    /**
     * 上传MultipartFile
     *
     * @param file  文件
     * @param key   存储文件名
     * @param override  是否覆盖
     * @return  返回存储路径
     * @throws IOException   异常
     */
    public String uploadMultipartFile(MultipartFile file, String key, boolean override) {
        //把文件转化为字节数组
        InputStream is;
        try {
            is = file.getInputStream();
            byte[] uploadBytes = IOUtils.toByteArray(is);
            Auth auth = getAuth();
            String upToken;
            if (override) {
                upToken = auth.uploadToken(bucket, key);//覆盖上传凭证
            } else {
                upToken = auth.uploadToken(bucket);
            }
            //默认上传接口回复对象
            DefaultPutRet putRet;
            //进行上传操作，传入文件的字节数组，文件名，上传空间，得到回复对象
            Response response = uploadManager.put(uploadBytes, key, upToken);
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件{},上传成功", putRet.key);
            String fileUrl = getFileUrl(key);

            InternetResources internetResources = new InternetResources();
            internetResources.setName(file.getOriginalFilename());
            internetResources.setUrl(fileUrl);
            //资源类型
            int type;
            String originalFilename = file.getOriginalFilename();
            Objects.requireNonNull(originalFilename);
            if (originalFilename.endsWith(".mp3")) {
                type = 0;
            } else if (originalFilename.endsWith(".mp4")) {
                type = 1;
            } else {
                type = 2;
            }
            internetResources.setType(type);
            internetResourcesDao.insert(internetResources);
            return fileUrl;
        } catch (IOException e) {
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @return  存储路径
     */
    public String getFileUrl(String fileName) {
        return String.format("%s/%s", domainOfBucket, fileName);
    }

    /**
     * 获取所有文件
     *
     * @return  返回搜索文件立标
     * @throws QiniuException   异常信息
     */
    public List<String> findFiles() {
        Auth auth = getAuth();
        BucketManager bucketManager = new BucketManager(auth, new Configuration(Zone.zone1()));
        FileListing listing = null;
        try {
            listing = bucketManager.listFiles(bucket, null, null,
                    10, null);
        } catch (QiniuException e) {
            Response r = e.response;
            throw new BizException("查询所有文件异常，异常信息" + r.toString());
        }
        if (listing == null || listing.items == null
                || listing.items.length <= 0) {
            return null;
        }
        List<String> fileList = new ArrayList<>();
        Arrays.stream(listing.items).forEach(a -> fileList.add(a.key));
        return fileList;
    }

}
