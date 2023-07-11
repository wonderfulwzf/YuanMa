package sping;

import wzf.service.UserService;

public class WzfApplicationContext {


    private final Class configClass;


    public WzfApplicationContext(Class configClass) {
        this.configClass = configClass;
    }

    public Object getBean(String beanName){
        return new UserService();
    }

}
