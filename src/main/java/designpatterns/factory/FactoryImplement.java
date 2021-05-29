package designpatterns.factory;

import java.math.BigDecimal;

public class FactoryImplement implements FactoryInterface{
    @Override
    public Number parse(String s) {
        return new BigDecimal(s);
    }
    //获取工厂实例:
    //common factory
    //工厂方法可以隐藏创建产品的细节，且不一定每次都会真正创建产品，完全可以返回缓存的产品，从而提升速度并减少内存消耗。
    static FactoryImplement getFactory() {
        return impl;
    }

    static FactoryImplement impl = new FactoryImplement();

    //static factory
    public static Number staticParse(String s){
        return new BigDecimal(s);
    }
}
