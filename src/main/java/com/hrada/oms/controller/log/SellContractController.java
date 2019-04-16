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
import com.hrada.oms.service.PersonalService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/11/22.
 */
@Controller
@RequestMapping("/contract/sell")
public class SellContractController {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    PersonalService personalService;

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
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "/log/contract/sell/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("personal", user.getPersonal());
        model.addAttribute("sellers", personalService.findSeller());
        model.addAttribute("clients", clientRepository.findAll());
        return "/log/contract/sell/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("contract", contractRepository.findOne(id));
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        return "/log/contract/sell/detail";
    }

    @PostMapping("/save")
    public String save(Contract contract){
        contractService.save(contract);
        return "redirect:/contract/sell/list";
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
                           @RequestParam(value = "client", required = false) final Long client,
                           @RequestParam(value = "seller", required = false) final Long seller){
        return contractService.sellData(pageNumber, pageSize, seller, client);
    }
}
