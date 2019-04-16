package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.ClientRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.model.Client;
import com.hrada.oms.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/9/10.
 */
@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    PersonalRepository personalRepository;


    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("client",new Client());
        model.addAttribute("personals",personalRepository.findAll());
        return "/model/client/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/model/client/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("client",clientRepository.findOne(id));
        model.addAttribute("personals",personalRepository.findAll());
        return "/model/client/detail";
    }

    @PostMapping("/save")
    public String save(Client client){
        clientRepository.save(client);
        return "redirect:/client/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            clientRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "name", required = false) String name){
        return clientService.data(pageNumber, pageSize, name);
    }
}
