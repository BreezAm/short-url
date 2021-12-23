package com.breez.shorturl;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class Gen {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/shorturl", "root", "root")
                .globalConfig(builder -> {
                    builder.author("BreezAm") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:/shorturl/file/gen/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.breez") // 设置父包名
                            .moduleName("shorturl") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "E:/shorturl/file/gen/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("su_analysis" ) // 设置需要生成的表名
                            .addTablePrefix("t_", "su_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
