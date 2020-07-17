package com.baizhi.util;


import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SearchForElasticsearch {

    public static <T> List<T> queryInfo(Class<T> clazz,
                                        ElasticsearchTemplate elasticsearchTemplate,
                                        String indices,
                                        String type,
                                        String searchField,
                                        String searchFieldValue
                                        //String descField
    ) {
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        // 构建查询对象
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(indices).withTypes(type) // 指定操作的索引名  和  类型 名
                .withQuery(QueryBuilders.matchQuery(searchField, searchFieldValue))// 指定查询的方式 key和value
                //.withQuery(QueryBuilders.queryStringQuery(content).field(key1).field(key2)) 指定查询方式和字段 可以匹配两个字段
                .withPageable(PageRequest.of(0, 100))
                //.withSort(SortBuilders.fieldSort(descField).order(SortOrder.DESC))  //descField 根据这个键排序
                .withHighlightFields(field) //高亮
                .build();
        // 执行查询
        AggregatedPage<T> aggregatedPage = elasticsearchTemplate.queryForPage(searchQuery, clazz,
                new SearchResultMapper() {
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                        // 获取查询结果中的所有文档
                        SearchHit[] hits = response.getHits().getHits();
                        List<T> list = new ArrayList<>();
                        for (SearchHit hit : hits) {
                            // 获取id
                            String id = hit.getId();
                            // 获取原生的结果
                            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                            sourceAsMap.put("id", id);
                            // 获取高亮字段
                            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                            Set<Map.Entry<String, Object>> entries = sourceAsMap.entrySet();
                            for (Map.Entry<String, Object> entry : entries) {
                                // 获取高亮字段
                                HighlightField highlightField = highlightFields.get(entry.getKey());
                                if (highlightField != null) {
                                    sourceAsMap.put(entry.getKey(), highlightField.fragments()[0].toString());
                                }
                            }
                            /**
                             * 封装结果集
                             */
                            try {
                                // 自定义结果集的封装
                                // 反射创建类的对象
                                T t = clazz.newInstance();
                                // 反射获取所有的类中的字段
                                Field[] fields = clazz.getDeclaredFields();
                                for (Field field : fields) {
                                    // 设置运行反射操作属性
                                    field.setAccessible(true);
                                    // 获取属性名
                                    String name = field.getName();
                                    // 获取属性类型
                                    Class<?> type = field.getType();
//                                    System.out.println(name+"的类型是："+type);
                                    // 根据当前这个字段的名字，到map中获取值，而得到的值就是要封装到当前字段的值
                                    Object value = sourceAsMap.get(name);
                                    // 给对象的属性反射赋值的方法 : 参数一 需要指定操作的对象   参数二 是给属性的赋值
                                    if (type.equals(Double.class)) {
                                        String strValue = (String) value;
                                        Double doubleValue = Double.valueOf(strValue);
                                        field.set(t, doubleValue);
                                    } else if (type.equals(Integer.class)) {
                                        String strValue = (String) value;
                                        Integer integerValue = Integer.valueOf(strValue);
                                        field.set(t, integerValue);
                                    } else if (type.equals(Date.class)) {
                                        String strValue = (String) value;
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = dateFormat.parse(strValue);
                                        field.set(t, date);
                                    } else {
                                        field.set(t, value);
                                    }
                                }

                                list.add(t);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        return new AggregatedPageImpl(list);
                    }
                });
        return aggregatedPage.getContent();
    }


}
