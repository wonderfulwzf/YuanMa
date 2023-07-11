package wzf.service;

import sping.Autowired;
import sping.Component;
import sping.Scope;

@Component("userService")
@Scope
public class UserService {

    @Autowired
    private OrderService orderService;
}
