bean的创建生命周期
UserService.class
无参构造
生成对象
依赖注入(属性赋值)

初始化前

初始化（InitializingBean）

初始化后（aop）

若进行aop则生成代理对象

bean

销毁




实现InitializingBean接口，重写afterPropertiesSet()
也可以进行属性注入后的赋值


userService instanceof InitializingBean   (InitializingBean)对象.方法


aop  cglib  原理是继承  成员变量（  目标对象  target ）  生成代理对象   用目标对象（target）调用执行方法

事务的有效性   针对于代理对象


@EnableAspectJAutoProxy  开启aop,进行代理

@EnableTransactionManagement   开启事务注解
