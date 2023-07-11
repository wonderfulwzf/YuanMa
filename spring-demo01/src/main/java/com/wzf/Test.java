package com.wzf;

import com.wzf.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

/*        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");*/

        UserService userService = (UserService) context.getBean("userService");

        //userService.test();
        userService.jdbcMethod();

        //遍历属性
       /* Field[] fields = userService.getClass().getFields();
        for(Field field : fields){
            if (field.isAnnotationPresent(Autowired.class)){
                System.out.println(field);
            }
        }*/

        //遍历方法
        //Method[] methods = userService.getClass().getMethods();


    }
}
