package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.EnumRepository;
import com.hrada.oms.dao.log.ContractRepository;
import com.hrada.oms.dao.log.PaymentRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Payment;
import com.hrada.oms.service.PaymentService;
import com.hrada.oms.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by shin on 2018/10/16.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    EnumRepository enumRepository;

    @Autowired
    DateUtil dateUtil;

    @RequestMapping("/chart")
    public String chart(Model model, @RequestParam(value = "year", required = false) String year){
        if(year==null){
            year = dateUtil.getCurrentDate("yyyy");
        }
        Integer y = Integer.valueOf(year);
        model.addAttribute("pie", paymentService.getPieData(y));
        model.addAttribute("line", paymentService.getLineData(y));
        model.addAttribute("year", y);
        return "/log/payment/chart";
    }

    @RequestMapping("/pass")
    public String pass(@RequestParam(value = "id") Long id){
        paymentService.pass(id);
        return "redirect:/payment/list";
    }

    @RequestMapping("/refuse")
    public String refuse(@RequestParam(value = "id") Long id){
        paymentService.refuse(id);
        return "redirect:/payment/list";
    }

    @RequestMapping("/reSub")
    public String reSub(@RequestParam(value = "id") Long id){
        paymentService.reSub(id);
        return "redirect:/payment/list";
    }

    @RequestMapping("/add")
    public String add(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Payment payment = new Payment();
        payment.setLogDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
        model.addAttribute("update", true);
        model.addAttribute("payment", payment);
        model.addAttribute("user", user);
        model.addAttribute("contracts", contractRepository.findAllByTypeOrderByCreateDateDesc(0));
        model.addAttribute("companies", enumRepository.findByParent_Name("所属公司"));
        return "/log/payment/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("personal", user.getPersonal());
        model.addAttribute("personals", personalRepository.findAll());
        return "/log/payment/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Payment payment = paymentRepository.findOne(id);
        model.addAttribute("update", payment.getApplicant().getId() == user.getPersonal().getId());
        model.addAttribute("payment", payment);
        model.addAttribute("user", user);
        model.addAttribute("contracts",contractRepository.findAllByTypeOrderByCreateDateDesc(0));
        model.addAttribute("companies", enumRepository.findByParent_Name("所属公司"));
        return "/log/payment/detail";
    }

    @PostMapping("/save")
    public String save(Payment payment){
        paymentService.save(payment);
        return "redirect:/payment/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            paymentRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "logDate", required = false) Date logDate,
                           @RequestParam(value = "applicant", required = false) Long applicant,
                           @RequestParam(value = "receipt", required = false) String receipt){
        return paymentService.data(pageNumber, pageSize, logDate, receipt, applicant);
    }
}
