package com.hrada.oms.controller.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.PermissionRepository;
import com.hrada.oms.dao.common.RoleRepository;
import com.hrada.oms.model.common.Permission;
import com.hrada.oms.model.common.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shin on 2018/8/9.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Role());
        model.addAttribute("permissions", permissionRepository.findAll());
        return "/common/role/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/common/role/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        Role role = roleRepository.findOne(id);
        List<Permission> permissions = permissionRepository.findAll();
        Set<Permission> list = role.getPermissionList();
        if(list!=null && permissions!=null){
            for(Permission permission:permissions){
                for(Permission r:list){
                    if(permission.getId()==r.getId()){
                        permission.setFlag(1);
                    }
                }
            }
        }
        model.addAttribute("role", role);
        model.addAttribute("permissions", permissions);
        return "/common/role/detail";
    }

    @PostMapping("/save")
    public String save(Role e, @RequestParam(name="permissions", required=false, defaultValue="") String permissions){
        Set<Permission> permissionList = new HashSet<>();
        if(!permissions.equals("")) {
            String[] array = permissions.split(",");
            for (String id : array) {
                Permission permission = permissionRepository.getOne(new Long(id));
                permissionList.add(permission);
            }
        }
        e.setPermissionList(permissionList);
        e = roleRepository.save(e);
        return "redirect:/role/" + e.getId();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            roleRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Role> page = roleRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
