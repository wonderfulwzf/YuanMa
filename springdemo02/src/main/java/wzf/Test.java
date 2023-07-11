package wzf;

import sping.WzfApplicationContext;
import wzf.service.UserService;

public class Test {
    public static void main(String[] args) {
        WzfApplicationContext context = new WzfApplicationContext(Appconfig.class);
        UserService userService = (UserService) context.getBean("userService");
    }
}
