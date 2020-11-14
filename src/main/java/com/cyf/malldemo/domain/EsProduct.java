package com.cyf.malldemo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 搜索中的商品信息
 * 标示映射到Elasticsearch文档上的领域对象
 * public @interface Document {
 * 索引库名次，mysql中数据库的概念
 * String indexName();
 * 文档类型，mysql中表的概念
 * *     String type() default "";
 * 默认分片数
 * short shards() default 5;
 * 默认副本数量
 * short replicas() default 1;
 * <p>
 * }
 *
 * @author by cyf
 * @date 2020/8/3.
 */
@Document(indexName = "pms", type = "product", shards = 1, replicas = 0)
@Data
public class EsProduct implements Serializable {
    public static final long serialVersionUID = -1L;

    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String productSn;
    private Long brandId;
    @Field(type = FieldType.Keyword)
    private String brandName;
    private Long productCategoryId;
    @Field(type = FieldType.Keyword)
    private String productCategoryName;
    private String pic;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String name;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String subTitle;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String keywords;
    private BigDecimal price;
    private Integer sale;
    private Integer newStatus;
    private Integer recommandStatus;
    private Integer stock;
    private Integer promotionType;
    private Integer sort;
    @Field(type = FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;
}
