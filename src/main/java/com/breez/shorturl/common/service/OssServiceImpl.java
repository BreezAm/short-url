package com.breez.shorturl.common.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.breez.shorturl.config.OssConfig;
import org.iherus.codegen.qrcode.SimpleQrcodeGenerator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements IFileService {
    @Autowired
    private UserSessionService userSessionService;

    /**
     * 上传短链接二维码
     *
     * @param data
     * @param code
     */
    @Override
    public String uploadQR(String data, String code,String userType) {
        String username = userSessionService.getCurrentUser().getUsername();
        String endpoint = OssConfig.END_POINT;
        String accessKeyId = OssConfig.ACCESS_KEY_ID;
        String accessKeySecret = OssConfig.ACCESS_KEY_SECRET;
        String bucketName = OssConfig.BUCKET_NAME;

        String fileName = "";
        String group = new DateTime().toString("yyyy/MM/dd");
        String id = UUID.randomUUID().toString().substring(0, 10).replaceAll("-", "");
        fileName = username+"/qr/" + group + "/" + id + code + ".png";

        OSS ossClient = null;
        try {
            BufferedImage image = new SimpleQrcodeGenerator().generate(data).getImage();
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(image, "png", imOut);
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bs.toByteArray());

            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, fileName, arrayInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return "https://" + bucketName + "." + endpoint + "/" + fileName;
    }
}
