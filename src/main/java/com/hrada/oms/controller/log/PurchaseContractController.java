package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.ContractRepository;
import com.hrada.oms.dao.model.ClientRepository;
import com.hrada.oms.dao.model.ProductRepository;
import com.hrada.oms.dao.model.SupplierRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Contract;
import com.hrada.oms.service.ContractService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/11/22.
 */
@Controller
@RequestMapping("/contract/purchase")
public class PurchaseContractController {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ContractService contractService;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/pass")
    public String pass(@RequestParam(value = "id") Long id){
        contractService.pass(id);
        return "redirect:/contract/sell/list";
    }

    @RequestMapping("/refuse")
    public String refuse(@RequestParam(value = "id") Long id){
        contractService.refuse(id);
        return "redirect:/contract/sell/list";
    }

    @RequestMapping("/reSub")
    public String reSub(@RequestParam(value = "id") Long id){
        contractService.reSub(id);
        return "redirect:/contract/sell/list";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("contract",new Contract());
        model.addAttribute("suppliers",supplierRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("sellContracts", contractRepository.findAllByTypeOrderByCreateDateDesc(1));
        return "/log/contract/purchase/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("personal", user.getPersonal());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "/log/contract/purchase/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("contract",contractRepository.findOne(id));
        model.addAttribute("suppliers",supplierRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("sellContracts", contractRepository.findAllByTypeOrderByCreateDateDesc(1));
        return "/log/contract/purchase/detail";
    }

    @PostMapping("/save")
    public String save(Contract contract){
        contractService.save(contract);
        return "redirect:/contract/purchase/list/";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            contractRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "supplier", required = false) Long  supplier){
        return contractService.purchaseData(pageNumber, pageSize, supplier);
    }
}
