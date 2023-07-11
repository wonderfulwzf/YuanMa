package wzf.service;

import sping.BeanPostProcessor;
import sping.Component;

@Component
public class WzfBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitializingBean(Object bean, String beanName) {
        return BeanPostProcessor.super.postProcessBeforeInitializingBean(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitializingBean(Object bean, String beanName) {
        System.out.println("初始化后");
        return bean;
    }
}
