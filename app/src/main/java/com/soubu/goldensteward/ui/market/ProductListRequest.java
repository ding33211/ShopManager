package com.soubu.goldensteward.ui.market;

import com.soubu.goldensteward.support.web.BasePageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作者：余天然 on 2016/12/14 下午8:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductListRequest extends BasePageRequest {

    private String id;

    public ProductListRequest(int page, String id) {
        super(page);
        this.id = id;
    }
}
