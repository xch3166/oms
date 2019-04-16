package com.hrada.oms.controller.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.MessageRepository;
import com.hrada.oms.model.common.Message;
import com.hrada.oms.model.common.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by shin on 2018/9/10.
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;


    @RequestMapping("/read")
    @ResponseBody
    public String read(@RequestParam("id") Long id, HttpServletRequest request){
        Message message = messageRepository.findOne(id);
        message.setState(1);
        messageRepository.save(message);

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Message> list = messageRepository.findAllByUserAndStateOrderByIdDesc(user, 0);
        request.getSession().setAttribute("message", list);
        request.getSession().setAttribute("count", list.size());
        return "ok";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("message",new Message());
        return "/common/message/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/common/message/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id, HttpServletRequest request){
        Message message = messageRepository.findOne(id);
        model.addAttribute("message", message);
        message.setState(1);
        messageRepository.save(message);

        HttpSession session = request.getSession();
        Integer count = (Integer) session.getAttribute("count");
        session.setAttribute("count",count>1?count-1:0);
        List<Message> list = (List<Message>) session.getAttribute("message");
        list.remove(message);
        session.setAttribute("message", list);

        return "/common/message/detail";
    }

    @PostMapping("/save")
    public String save(Message message){
        messageRepository.save(message);
        return "redirect:/message/list";
    }

    @RequestMapping("/readAll")
    @ResponseBody
    public String readAll(@RequestParam("array") String ids, HttpServletRequest request){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            Message message = messageRepository.findOne(o.getLong("id"));
            message.setState(1);
            messageRepository.save(message);
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Message> list = messageRepository.findAllByUserAndStateOrderByIdDesc(user, 0);
        request.getSession().setAttribute("message", list);
        request.getSession().setAttribute("count", list.size());
        return "ok";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            messageRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Page<Message> page = messageRepository.findAllByUser(pageable, user);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
