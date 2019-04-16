package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.ProductCategoryRepository;
import com.hrada.oms.model.model.ProductCategory;
import com.hrada.oms.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2019-03-11.
 */
@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping("/tree")
    @ResponseBody
    public JSON tree(@RequestParam(value = "id") Long id ){
        return productCategoryService.tree(id);
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new ProductCategory());
        return "/model/productCategory/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/model/productCategory/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("productCategory", productCategoryRepository.findOne(id));
        return "/model/productCategory/detail";
    }

    @PostMapping("/save")
    public String save(ProductCategory productCategory){
        productCategoryRepository.save(productCategory);
        return "redirect:/productCategory/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam long id){
        productCategoryRepository.delete(id);
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<ProductCategory> page = productCategoryRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
