package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.ProjectDetailRepository;
import com.hrada.oms.dao.log.ProjectRepository;
import com.hrada.oms.dao.model.ClientRepository;
import com.hrada.oms.dao.model.SupplierRepository;
import com.hrada.oms.model.log.Project;
import com.hrada.oms.model.log.ProjectDetail;
import com.hrada.oms.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shin on 2018/12/7.
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectDetailRepository projectDetailRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ProjectService projectService;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("project", new Project());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "/log/project/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "/log/project/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("project", projectRepository.findOne(id));
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "/log/project/detail";
    }

    @PostMapping("/save")
    public String save(Project project){
        projectService.save(project);
        return "redirect:/project/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            projectRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "supplier", required = false) final Long  supplier){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");

        Specification specification = new Specification(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                Expression<Long> exp = root.<Long>get("id");
                List<Long> idList = new ArrayList<>();
                List<ProjectDetail> details = projectDetailRepository.findAllBySupplier_Id(supplier);
                for(ProjectDetail detail:details){
                    idList.add(detail.getProject().getId());
                }
                if(idList.size()==0){
                    idList.add(new Long(9999999));
                }
                if(supplier!=null) {
                    predicates.add(exp.in(idList));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Project> page = projectRepository.findAll(specification, pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
