package com.example.demo.Nginx_Jmeter_分布式锁;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("reduceStock")
public class ReduceStockController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Redisson redisson;

    @GetMapping
    public String reduceStock() {

          String lockKey = "lockKey";
//        String clientId = UUID.randomUUID().toString();
//
//        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);//分布式锁1.0
//        //在并发条件下，只有最早到达的线程才能执行成功，此时返回true。此时后续到达的线程都无法执行成功，此时返回false。
//        //TODO redis是单线程模型，不管有多少个请求，redis都会按照到达的先后顺序对这些请求进行排队，然后依次执行。所以是同步的。
//        if (!result) {
//            return "error";//返回前端错误码或友好提示
//        }

        RLock lock = redisson.getLock(lockKey);//获取redis的锁对象，但是此时还没有向redis中设置这把锁
        try {
            lock.lock();//等效于：redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
            /*
                     会为每个线程生成对应的唯一id作为value。
                     锁的默认超时时间为30s(可以指定)。
                     只有一个线程可以执行，其他线程将会原地等待(自旋)。
             */
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            /*if (clientId.equals(redisTemplate.opsForValue().get(lockKey))) {
                redisTemplate.delete(lockKey);
            }*/
        }

        return "end";
    }
}
