package com.leyou.test;

import com.leyou.search.client.CategoryClient;
import com.leyou.search.pojo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class feignClientTest {
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void test(){
        List<String> list = categoryClient.queryNameByIds(Arrays.asList(1L, 2L, 3L));
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void createIndex(){
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }
}
