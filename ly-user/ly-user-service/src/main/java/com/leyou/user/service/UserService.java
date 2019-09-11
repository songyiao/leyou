package com.leyou.user.service;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private UserMapper userMapper;

    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                return null;
        }
        return userMapper.selectCount(user) == 0;
    }

    public Boolean sendCode(String phone) {
        // 生成验证码
        String code = NumberUtils.generateCode(6);
        //发送短信
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("phone",phone);
            map.put("code",code);
        //  amqpTemplate.convertAndSend("sms.verity.code",map);
            System.out.println(code);

            //将code存入redis
            redisTemplate.opsForValue().set("user:phone:code:"+phone,code,1,TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            log.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    public Boolean register(User user, String code) {
        String redisCode = redisTemplate.opsForValue().get("user:phone:code:" + user.getPhone());
        if(StringUtils.isBlank(redisCode) || !redisCode.equals(code)){
            return false;
        }
        user.setId(null);
        user.setCreated(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        Boolean bool = userMapper.insert(user) == 1;

        // 如果注册成功，删除redis中的code
        if(bool){
            try {
                redisTemplate.delete("user:phone:code:" + user.getPhone());
            }catch (Exception e){
                log.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
        return bool;
    }

    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        // 校验用户名
        if (user == null) {
            return null;
        }
        // 校验密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return null;
        }
        // 用户名密码都正确
        return user;
    }
}
