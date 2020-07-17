package com.baizhi.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    private Object data;
    private String message;
    private String status;

    public CommonResult success(Object data, String message, String status) {
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage(message);
        commonResult.setStatus(status);
        return commonResult;
    }

    public CommonResult success(Object data, String message) {
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage(message);
        commonResult.setStatus("100");
        return commonResult;
    }

    public CommonResult success(Object data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage("查询成功");
        commonResult.setStatus("100");
        return commonResult;
    }

    public CommonResult failed(Object data, String message, String status) {
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage(message);
        commonResult.setStatus(status);
        return commonResult;
    }

    public CommonResult failed(Object data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage("查询失败");
        commonResult.setStatus("104");
        return commonResult;
    }

    public CommonResult failed(Object data, String message) {
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage(message);
        commonResult.setStatus("104");
        return commonResult;
    }

    public CommonResult failed() {
        CommonResult commonResult = new CommonResult();
        commonResult.setData(null);
        commonResult.setMessage("查询失败");
        commonResult.setStatus("104");
        return commonResult;
    }
}
