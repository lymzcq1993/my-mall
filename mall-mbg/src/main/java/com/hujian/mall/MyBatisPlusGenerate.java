package com.hujian.mall;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成器
 * @author hujian
 */
public class MyBatisPlusGenerate {
    public static String PACKAGE_NAME = "com.hujian.mall.mapper";
    public static String MODULE_NAME = "ums";
    public static String AUTHOR = "hujian";
    /**
     * 多个用逗号分割
     */
    public static String TABLE_NAMES = "ums_admin";
    public static String OUTPUT_DIR = "D:/programes/oracle";

    public static String MAPPER_PATH = OUTPUT_DIR+"/mapper";

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://1.15.45.245:13306/myMall?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8", "hujian", "hujian");

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        FastAutoGenerator.create("jdbc:mysql://1.15.45.245:13306/myMall?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8",
                        "hujian","hujian")
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(AUTHOR).fileOverride().enableSwagger().outputDir(OUTPUT_DIR))
                // 包配置
                .packageConfig((scanner, builder) -> builder
                        .parent(PACKAGE_NAME)
                        .moduleName(MODULE_NAME)
                        .pathInfo(Collections.singletonMap(OutputFile.xml,MAPPER_PATH)))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(TABLE_NAMES)
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT),
                                new Column("update_time", FieldFill.INSERT_UPDATE)
                        )
                        .idType(IdType.AUTO)
                        .serviceBuilder().convertServiceFileName(entityName -> entityName+"Service")
                        .build())

                //    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
//                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())

                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
