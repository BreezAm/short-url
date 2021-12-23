package com.breez.shorturl;

import com.breez.shorturl.common.service.IFileService;
import io.minio.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
class ShortUrlApplicationTests {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    @Qualifier(value = "minioServiceImpl")
    private IFileService fileService;

    @Test
    void contextLoads() {

    }

    //判断桶是否存在
    @Test
    public void inset() throws Exception {
        boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket("666url").build());
        System.out.println(b);
    }

    @Test
    public void test2() throws Exception {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket("66url").build());

    }

    @Test
    public void upload() throws Exception {
        File f = new File("doc/image/1.png");
        FileInputStream file = new FileInputStream(f);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket("666url")
                .object("breeza.png")
                .stream(file, f.length(), -1)
                .contentType("image/jpeg;image/png")
                .build();
        minioClient.putObject(args);
    }

    @Test
    public void test3() {
    }

}
