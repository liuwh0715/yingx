package com.baizhi;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class goEazyTest {
    @Test
    public void sendMethod() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-113617c316ef4dedbd28fdee951ed9c1");
        goEasy.publish("hang_channel", "Hello, GoEasy!");
    }

    @Test
    public void sendMethod1() {
        //数据发生改变时从新查询结果并推送至前台
        //用户注册成功时

        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            random.nextInt(500);

            HashMap<String, Object> map = new HashMap<>();

            //根据性别查询每个月注册的人数
            map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
            map.put("boys", Arrays.asList(random.nextInt(400), random.nextInt(500), random.nextInt(300), random.nextInt(100), random.nextInt(200), random.nextInt(500)));
            map.put("girls", Arrays.asList(random.nextInt(500), random.nextInt(100), random.nextInt(500), random.nextInt(400), random.nextInt(200), random.nextInt(500)));

            //将map转为json字符串
            String content = JSON.toJSONString(map);

            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-113617c316ef4dedbd28fdee951ed9c1");
            goEasy.publish("hang_channel", content);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
