package com.leyou.search.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "leyou.search.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void mq(String msg){
        System.out.println(msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "leyou.search.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void mq2(String msg){
        System.out.println("delete:  "+msg);
    }
}
