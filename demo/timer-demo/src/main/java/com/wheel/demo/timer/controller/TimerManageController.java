package com.wheel.demo.timer.controller;

import com.wheel.common.vo.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc
 * @author: zhouf
 */
@Slf4j
@RestController
@RequestMapping("/timer")
public class TimerManageController {


    @RequestMapping("/listPage")
    public RestResult listPage() {
        return RestResult.success("");
    }
}
