package cn.itcast.modelgroup;

import cn.itcast.model.TbSku;
import cn.itcast.model.TbSpu;

import java.util.List;

public class Goods {

    //spu
    private TbSpu spu;

    //sku集合
    private List<TbSku> skuList;

    public TbSpu getSpu() {
        return spu;
    }

    public void setSpu(TbSpu spu) {
        this.spu = spu;
    }

    public List<TbSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<TbSku> skuList) {
        this.skuList = skuList;
    }
}
