package wzf.service;

import sping.Autowired;
import sping.Component;
import sping.InitializingBean;
import sping.Scope;

@Component("userService")
@Scope
public class UserService  implements InitializingBean {

    @Autowired
    private OrderService orderService;

    @Override
    public void afterPropertiesSet() {
        System.out.println("bean的实例化回调");
    }
}
