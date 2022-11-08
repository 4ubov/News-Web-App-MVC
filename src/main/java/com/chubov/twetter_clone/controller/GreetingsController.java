package com.chubov.twetter_clone.controller;

import com.chubov.twetter_clone.domain.Message;
import com.chubov.twetter_clone.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingsController {

    @Autowired
    private MessageRepo messageRepo;
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Map<String, Object> model)
    {
        model.put("name", name);
        return "greeting";
    }
    @GetMapping
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }
    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        //Сохраняем полученное сообщение из формы
        Message message = new Message(text, tag);
        messageRepo.save(message);
        //Выводим полученное сообщение
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages;
        //Если значение фильтра пустое или null, то вывести все сообщения
        if(filter==null || filter.isEmpty()){
            messages = messageRepo.findAll();
        }
        //Сортировка по фильтру (по тегу)
        else {
            messages = messageRepo.findByTag(filter);
        }
        model.put("messages", messages);
        return "main";
    }
}