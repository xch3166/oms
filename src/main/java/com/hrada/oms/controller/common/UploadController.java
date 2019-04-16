package com.hrada.oms.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by shin on 2018/9/19.
 */
@Controller
public class UploadController {

    @Autowired
    private UploadUtil uploadUtil;

    @PostMapping("/upload")
    @ResponseBody
    public JSONObject uploadFile(@RequestPart("file") MultipartFile file) {
        return uploadUtil.upload(file);
    }

    @RequestMapping(value = "/upload/img/cut")
    public JSONObject uploadCutImage(@RequestPart("file") MultipartFile file,
                                     @RequestParam(value = "x") Integer x,
                                     @RequestParam(value = "y") Integer y,
                                     @RequestParam(value = "w") Integer w,
                                     @RequestParam(value = "h") Integer h) {
        return uploadUtil.imgCut(file, x, y, w, h);
    }
}
