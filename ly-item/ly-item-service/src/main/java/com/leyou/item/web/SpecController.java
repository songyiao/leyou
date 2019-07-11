package com.leyou.item.web;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecGroupService;
import com.leyou.item.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecGroupService specGroupService;
    @Autowired
    private SpecParamService specParamService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> specGroupList = specGroupService.querySpecGroupByCid(cid);
        if(CollectionUtils.isEmpty(specGroupList)){
            throw  new LyException(ExceptionEnum.GROUP_NOT_FOUND);
        }
        return ResponseEntity.ok(specGroupList);
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParamList(
            @RequestParam(name = "gid",required = false)Long gid,
            @RequestParam(name = "cid",required = false)Long cid,
            @RequestParam(name = "searching",required = false)Boolean searching
    ){
        List<SpecParam> specParamList = specParamService.querySpecParamList(gid,cid,searching);
        if(CollectionUtils.isEmpty(specParamList)){
            throw  new LyException(ExceptionEnum.PARAMS_NOT_FOUND);
        }
        return ResponseEntity.ok(specParamList);
    }
}
