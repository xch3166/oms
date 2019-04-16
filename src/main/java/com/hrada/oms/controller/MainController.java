package com.hrada.oms.controller;

import com.hrada.oms.dao.common.MessageRepository;
import com.hrada.oms.dao.log.InjectionRepository;
import com.hrada.oms.model.common.Message;
import com.hrada.oms.model.common.User;
import com.hrada.oms.service.ChartService;
import com.hrada.oms.service.SafetyService;
import com.hrada.oms.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by shin on 2018/2/1.
 */
@Controller
public class MainController {

    @Autowired
    InjectionRepository injectionRepository;

    @Autowired
    MessageRepository messageRepository;

   @Autowired
    SafetyService safetyService;

    @Autowired
    ChartService chartService;

    @Autowired
    UserService userService;

    @RequestMapping({"/","/index"})
    public String index(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("injection", injectionRepository.getInjection());
        model.addAttribute("solid", injectionRepository.getSolid());
        model.addAttribute("liquid", injectionRepository.getLiquid());
        model.addAttribute("days", safetyService.getDays());
        model.addAttribute("line", chartService.getInjectionLine());
        return "/index";
    }

    @GetMapping("/403")
    public String noPermission() {
        return "403";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid User user, Boolean rememberMe, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUname(), user.getUpass(), rememberMe==null?false:rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            subject.getSession().setTimeout(86400000);
        } catch (UnknownAccountException uae) {
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (subject.isAuthenticated()) {
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = "/";
            if(savedRequest != null){
                url = savedRequest.getRequestUrl();
            }
            user = userService.findByUname(user.getUname());
            request.getSession().setAttribute("user", user);

            List<Message> list = messageRepository.findAllByUserAndStateOrderByIdDesc(user, 0);
            request.getSession().setAttribute("message", list);
            request.getSession().setAttribute("count", list.size());
            return "redirect:"+url;
        } else {
            token.clear();
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return "redirect:/login";
    }

    @RequestMapping("/upload")
    public String upload(){
        return "/common/upload/list";
    }
}
