package wzf;

import sping.WzfApplicationContext;
import wzf.service.UserService;

public class Test {
    public static void main(String[] args) {
        //扫描并创建单例bean
        WzfApplicationContext context = new WzfApplicationContext(Appconfig.class);
        UserService userService = (UserService) context.getBean("userService");
        UserService userService1 = (UserService) context.getBean("userService");
        System.out.println(userService);
        System.out.println(userService1);
    }
}
