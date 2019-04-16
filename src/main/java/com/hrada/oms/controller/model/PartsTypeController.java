package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.PartsTypeRepository;
import com.hrada.oms.model.model.PartsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
@Controller
@RequestMapping("/partsType")
public class PartsTypeController {

    @Autowired
    PartsTypeRepository partsTypeRepository;

    @RequestMapping("/tree")
    @ResponseBody
    public JSON tree(@RequestParam(value = "id") String id ){
        List<PartsType> list = partsTypeRepository.findAllByParentIsNull();
        JSONObject top = new JSONObject();
        top.put("id",null);
        top.put("text","无");
        JSONObject state = new JSONObject();
        state.put("open", true);
        top.put("state",state);
        JSONArray array = new JSONArray();
        for(PartsType partsType:list){
            JSONObject object = new JSONObject();
            object.put("id",partsType.getId());
            object.put("text",partsType.getName());
            if(Long.valueOf(id)==partsType.getId()){
                JSONObject select = new JSONObject();
                select.put("selected", true);
                object.put("state", select);
            }
            List<PartsType> list1 = partsTypeRepository.findByParent(partsType);
            if(list1!=null && list1.size()>0){
                JSONArray array1 = new JSONArray();
                for(PartsType partsType1:list1){
                    JSONObject object1 = new JSONObject();
                    object1.put("id",partsType1.getId());
                    object1.put("text",partsType1.getName());
                    if(Long.valueOf(id)==partsType1.getId()){
                        JSONObject select1 = new JSONObject();
                        select1.put("selected", true);
                        object1.put("state", select1);
                    }
                    List<PartsType> list2 = partsTypeRepository.findByParent(partsType1);
                    if(list2!=null && list2.size()>0){
                        JSONArray array2 = new JSONArray();
                        for(PartsType partsType2:list2){
                            JSONObject object2 = new JSONObject();
                            object2.put("id",partsType2.getId());
                            object2.put("text",partsType2.getName());
                            if(Long.valueOf(id)==partsType2.getId()){
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
        model.addAttribute(new PartsType());
        return "/model/partsType/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("partsType",partsTypeRepository.findAll());
        return "/model/partsType/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("partsType",partsTypeRepository.findOne(id));
        return "/model/partsType/detail";
    }

    @PostMapping("/save")
    public String save(PartsType partsType){
        partsTypeRepository.save(partsType);
        return "redirect:/partsType/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            partsTypeRepository.delete(o.getLong("id"));
        }
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<PartsType> page = partsTypeRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
