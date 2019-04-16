package com.hrada.oms.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.EnumRepository;
import com.hrada.oms.model.common.Enum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by shin on 2018/8/9.
 */
@Controller
@RequestMapping("/enum")
public class EnumController {

    @Autowired
    EnumRepository enumRepository;

    @RequestMapping("/tree")
    @ResponseBody
    public JSON tree(@RequestParam(value = "id", required = false) String id ){
        List<Enum> list = enumRepository.findAllByParentIsNull();
        JSONObject top = new JSONObject();
        top.put("id",null);
        top.put("text","无");
        JSONObject state = new JSONObject();
        state.put("open", true);
        top.put("state",state);
        JSONArray array = new JSONArray();
        for(Enum e:list){
            JSONObject object = new JSONObject();
            object.put("id",e.getId());
            object.put("text",e.getName());
            if(Long.valueOf(id)==e.getId()){
                JSONObject select = new JSONObject();
                select.put("selected", true);
                object.put("state", select);
            }
            List<Enum> list1 = enumRepository.findByParent(e);
            if(list1!=null && list1.size()>0){
                JSONArray array1 = new JSONArray();
                for(Enum enum1:list1){
                    JSONObject object1 = new JSONObject();
                    object1.put("id",enum1.getId());
                    object1.put("text",enum1.getName());
                    if(Long.valueOf(id)==enum1.getId()){
                        JSONObject select1 = new JSONObject();
                        select1.put("selected", true);
                        object1.put("state", select1);
                    }
                    List<Enum> list2 = enumRepository.findByParent(enum1);
                    if(list2!=null && list2.size()>0){
                        JSONArray array2 = new JSONArray();
                        for(Enum enum2:list2){
                            JSONObject object2 = new JSONObject();
                            object2.put("id",enum2.getId());
                            object2.put("text",enum2.getName());
                            if(Long.valueOf(id)==enum2.getId()){
                                JSONObject select2 = new JSONObject();
                                select2.put("selected", true);
                                object2.put("state", select2);
                            }
                            array2.add(object2);
                        }
                        object1.put("children",array2);
                    }
                    array1.add(object1);
                }
                object.put("children",array1);
            }
            array.add(object);
        }
        top.put("children",array);
        return top;
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Enum());
        model.addAttribute("parents", enumRepository.findAll());
        return "/common/enum/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("enum",enumRepository.findAll());
        return "/common/enum/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("enum",enumRepository.findOne(id));
        model.addAttribute("parents", enumRepository.findAll());
        return "/common/enum/detail";
    }

    @PostMapping("/save")
    public String save(Enum e){
        e = enumRepository.save(e);
        return "redirect:/enum/" + e.getId();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            enumRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Enum> page = enumRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
