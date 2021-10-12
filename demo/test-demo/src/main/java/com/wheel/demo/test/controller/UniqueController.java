package com.wheel.demo.test.controller;

import com.wheel.common.vo.api.ResponseResult;
import com.wheel.facade.unique.service.UniqueFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc
 * @author: zhouf
 */
@Slf4j
@RestController
@RequestMapping("/unique")
public class UniqueController {

    @Reference(mock = "")
    UniqueFacade uniqueFacade;

    @RequestMapping("/getUUID")
    public ResponseResult getUUID() {
        uniqueFacade.getUUID();
        return ResponseResult.success(uniqueFacade.getUUID());
    }
}
