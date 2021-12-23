package com.breez.shorturl.common.service;

import com.breez.shorturl.config.MinioProperties;
import com.breez.shorturl.constants.Constants;
import com.breez.shorturl.enums.ResponseCode;
import com.breez.shorturl.exceptions.ShortUrlException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.iherus.codegen.qrcode.SimpleQrcodeGenerator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
public class MinioServiceImpl implements IFileService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private MinioProperties minioProperties;
    private final Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

    /**
     * 上传二维码
     *
     * @param data 数据
     * @param code 唯一后缀
     * @return 地址
     */
    @Override
    public String uploadQR(String data, String code, String userType) {
        String fileName = "";
        String username = "";
        try {
            if (Constants.USER_TYPE_TEMP.equals(userType)) {
                username = Constants.USER_TYPE_TEMP;
            } else {
                username = userSessionService.getCurrentUser().getUsername();
            }
            String group = new DateTime().toString("yyyy/MM/dd");
            String id = UUID.randomUUID().toString().substring(0, 10).replaceAll("-", "");
            fileName = username + "/qr/" + group + "/" + id + code + ".png";

            BufferedImage image = new SimpleQrcodeGenerator().generate(data).getImage();
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(image, "png", imOut);
            ByteArrayInputStream in = new ByteArrayInputStream(bs.toByteArray());

            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(fileName)
                    .stream(in, in.available(), -1)
                    .contentType("image/jpeg;image/png")
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            logger.error("上传文件到minio服务器失败");
            throw new ShortUrlException(ResponseCode.MINIO_UPLOAD_ERROR.getCode(), ResponseCode.MINIO_UPLOAD_ERROR.getMsg());
        }
        return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + fileName;
    }

    public static void main(String[] args) {
        System.out.println(false);
    }
}
