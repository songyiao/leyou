package com.leyou.item.mq;

import com.leyou.item.web.PageAction;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertListener {

    @Autowired
    private PageAction pageAction;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "leyou.page.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void creatHtml(String msg){
        pageAction.createHtml(msg);
    }
}
