package com.wheel.gateway.filters.global;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

/**
 * @description 全局过滤器抽象类
 * 对于全局过滤器，只需要继承GlobalFilter、Ordered，并通过 @Component 注解使用即可
 * 如果希望更加灵活的使用，比如通过配置文件生效，则采用过滤器工厂模式
 * 可参考spring-cloud-gateway内置的一些过滤器工厂加以实现
 * @author: zhouf
 */
public abstract class AbstractGlobalFilter implements GlobalFilter, Ordered {
}
