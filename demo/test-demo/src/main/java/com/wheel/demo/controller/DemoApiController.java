package com.wheel.demo.controller;

import com.wheel.common.enums.api.VersionEnum;
import com.wheel.common.vo.api.RequestParam;
import com.wheel.common.vo.api.ResponseParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 测试对外接口
 * @author: zhouf
 * @date: 2020/9/8
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoApiController {

    @RequestMapping("/ok")
    public ResponseParamVo ok(@RequestBody RequestParam requestVo) {
        if (VersionEnum.V1_0.getValue().equals(requestVo.getVersion())) {
            return ResponseParamVo.success();
        } else {
            return ResponseParamVo.fail("此版本已下线！");
        }
    }

    @RequestMapping("/no")
    public ResponseParamVo no() {
        return ResponseParamVo.success("no");
    }
}
