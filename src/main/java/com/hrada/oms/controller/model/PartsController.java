package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.EquipmentRepository;
import com.hrada.oms.dao.model.ModuleRepository;
import com.hrada.oms.dao.model.PartsRepository;
import com.hrada.oms.dao.model.PartsTypeRepository;
import com.hrada.oms.model.model.Equipment;
import com.hrada.oms.model.model.Module;
import com.hrada.oms.model.model.Parts;
import com.hrada.oms.model.model.PartsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
@Controller
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    PartsRepository partsRepository;

    @Autowired
    PartsTypeRepository partsTypeRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @RequestMapping("/findByModule")
    @ResponseBody
    public JSONArray findByModule(@RequestParam("id") Long id){
        JSONArray array = new JSONArray();
        List<Parts> list = partsRepository.findAll();
        for(Parts p:list){
            String[] idArray = new String[]{};
            String modules = p.getModules();
            if(modules!=null && !modules.equals("")){
                idArray = modules.split(",");
            }
            if(Arrays.asList(idArray).contains(id.toString())) {
                JSONObject obj = new JSONObject( );
                obj.put("id", p.getId( ));
                obj.put("name", p.getName( ));
                array.add(obj);
            }
        }
        return array;
    }

    private void toJsonArray(JSONArray array, List<Parts> list) {
        for(Parts p:list){
            JSONObject obj = new JSONObject();
            obj.put("id", p.getId());
            obj.put("name", p.getName());
            array.add(obj);
        }
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public JSONArray findAll(){
        JSONArray array = new JSONArray();
        List<Parts> list = partsRepository.findAll();
        toJsonArray(array, list);
        return array;
    }

    @RequestMapping("/tree")
    @ResponseBody
    public JSON tree(@RequestParam(value = "ids") String ids){
        String[] idArray = new String[]{};
        if(ids!=null && !ids.equals("")){
            idArray = ids.split(",");
        }
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
            if(Arrays.asList(idArray).contains(partsType.getId().toString())){
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
                    if(Arrays.asList(idArray).contains(partsType1.getId().toString())){
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
                            if(Arrays.asList(idArray).contains(partsType2.getId().toString())){
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
        Parts parts = new Parts();
        List<Equipment> equipmentList = equipmentRepository.findAll();
        JSONArray eArray = new JSONArray();
        for(Equipment equipment:equipmentList){
            JSONObject object = new JSONObject();
            object.put("id", equipment.getId());
            object.put("name", equipment.getName());
            object.put("selected", false);
            eArray.add(object);
        }

        List<Module> moduleList = moduleRepository.findAll();
        JSONArray mArray = new JSONArray();
        for(Module module:moduleList){
            JSONObject object = new JSONObject();
            object.put("id", module.getId());
            object.put("name", module.getName());
            object.put("selected", false);
            mArray.add(object);
        }
        model.addAttribute("parts", parts);
        model.addAttribute("equipment", eArray);
        model.addAttribute("module", mArray);
        return "/model/parts/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("parts",partsRepository.findAll());
        return "/model/parts/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        Parts parts = partsRepository.findOne(id);
        List<Equipment> equipmentList = equipmentRepository.findAll();
        JSONArray eArray = new JSONArray();
        for(Equipment equipment:equipmentList){
            String[] idArray = new String[]{};
            String equipments = parts.getEquipments();
            if(equipments!=null && !equipments.equals("")){
                idArray = equipments.split(",");
            }
            JSONObject object = new JSONObject();
            object.put("id", equipment.getId());
            object.put("name", equipment.getName());
            object.put("selected", Arrays.asList(idArray).contains(equipment.getId().toString()));
            eArray.add(object);
        }

        List<Module> moduleList = moduleRepository.findAll();
        JSONArray mArray = new JSONArray();
        for(Module module:moduleList){
            String[] idArray = new String[]{};
            String modules = parts.getModules();
            if(modules!=null && !modules.equals("")){
                idArray = modules.split(",");
            }
            JSONObject object = new JSONObject();
            object.put("id", module.getId());
            object.put("name", module.getName());
            object.put("selected", Arrays.asList(idArray).contains(module.getId().toString()));
            mArray.add(object);
        }
        model.addAttribute("parts", parts);
        model.addAttribute("equipment", eArray);
        model.addAttribute("module", mArray);
        return "/model/parts/detail";
    }

    @PostMapping("/save")
    public String save(Parts parts){
        partsRepository.save(parts);
        return "redirect:/parts/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            partsRepository.delete(o.getLong("id"));
        }
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Parts> page = partsRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
