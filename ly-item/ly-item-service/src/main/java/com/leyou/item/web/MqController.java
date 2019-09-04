package com.leyou.item.web;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MqController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("test/mq")
    public ResponseEntity<Map<String,Object>> mq(@RequestParam("msg") String msg){
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg",msg);
        amqpTemplate.convertAndSend("item.insert",msg);
        return ResponseEntity.ok(map);
    }

    @PostMapping("test/mq2")
    public ResponseEntity<Map<String,Object>> mq2(@RequestParam("msg") String msg){
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg",msg);
        amqpTemplate.convertAndSend("item.delete",msg);
        return ResponseEntity.ok(map);
    }
}
