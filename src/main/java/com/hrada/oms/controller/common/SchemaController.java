package com.hrada.oms.controller.common;

import com.hrada.oms.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shin on 2018/2/26.
 */
@Controller
@RequestMapping("schema")
public class SchemaController {

    @Autowired
    SchemaService schemaService;

    @RequestMapping("tables")
    public String getTables(){
        return "/schema/table";
    }

    @RequestMapping("columns")
    public String getColumns(){
        return "/schema/column";
    }

    @RequestMapping("table/list")
    @ResponseBody
    public String getTableList(){
        return schemaService.getTables().toString();
    }

    @RequestMapping("/{table}/column/list")
    @ResponseBody
    public String getColumnList(@PathVariable String table){
        return schemaService.getColumns(table).toString();
    }
}
