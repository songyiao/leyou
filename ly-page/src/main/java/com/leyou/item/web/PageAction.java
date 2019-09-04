package com.leyou.item.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("page")
public class PageAction {

    @Autowired
    private TemplateEngine templateEngine;

    public void createHtml(String msg){
        //打印流
        PrintWriter writer = null;

        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        // 创建thymeleaf上下文对象
        Context context = new Context();
        // 把数据放入上下文对象
        context.setVariables(map);
        // 创建输出流
        File file = new File("D:/temp/thymeleafTemplate", new Date().getTime() + ".html");
        //  判断文件是否存在
        if(file.exists()){
            file.delete();
        }
        try {
            writer = new PrintWriter(file);
            templateEngine.process("item",context,writer);
        }catch (Exception e){
            log.error("页面静态化出错!" + e);
        }finally {
            if(writer != null){
                writer.close();
            }
        }

    }

    @RequestMapping("{id}")
    public String item(Model model){
        model.addAttribute("msg","动态渲染!");
        return "item";
    }
}
