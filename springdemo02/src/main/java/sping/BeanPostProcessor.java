package sping;

public interface BeanPostProcessor {

    default Object postProcessBeforeInitializingBean(Object bean,String beanName){
        return bean;
    }

    default Object postProcessAfterInitializingBean(Object bean,String beanName){
        return bean;
    }
}
