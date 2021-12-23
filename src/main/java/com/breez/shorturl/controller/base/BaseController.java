package com.breez.shorturl.controller.base;

import com.breez.shorturl.util.R;
/**
 * 通用控制器
 * @author BreezAm
 */
public class BaseController {

    public R returnAjax(boolean result) {
        return result ? R.ok() : R.error();
    }
}
