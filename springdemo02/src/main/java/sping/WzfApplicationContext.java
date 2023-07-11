package sping;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class WzfApplicationContext {


    private final Class configClass;

    //BeanDefinitionMap
    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String,Object>  singletonObject = new HashMap<>();

    private LinkedList<BeanPostProcessor> beanPostProcessors = new LinkedList<>();


    public WzfApplicationContext(Class configClass) {
        this.configClass = configClass;
        //扫描
        scan(configClass);

        //找出单例bean,创建并加入单例池
        Iterator<Map.Entry<String, BeanDefinition>> iterator = beanDefinitionMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, BeanDefinition> next = iterator.next();
            String beanName = next.getKey();
            BeanDefinition beanDefinition = next.getValue();
            if ("singleton".equals(beanDefinition.getScope())){
                Object bean = createBean(beanName, beanDefinition);
                //保存进单例池
                singletonObject.put(beanName,bean);
            }

        }

    }



    private void scan(Class configClass) {
        //进行扫描
        if (configClass.isAnnotationPresent(ComponentScan.class)){
            //获取类上面需要扫描的包信息
            ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScan.value();

            path = path.replace(".","/");
            ClassLoader classLoader = WzfApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());
            if (file.isDirectory()){
                for (File f : file.listFiles()){
                    //获取包扫描下的class文件全路径名称
                    String absolutePath = f.getAbsolutePath();

                    absolutePath = absolutePath.substring(absolutePath.indexOf("wzf"),absolutePath.indexOf(".class"));

                    absolutePath = absolutePath.replace("\\",".");

                    Class<?> aClass = null;
                    //加载类
                    try {
                        aClass = classLoader.loadClass(absolutePath);

                        //判断类是否实现BeanPostProcessor接口
                        if (BeanPostProcessor.class.isAssignableFrom(aClass)){
                            BeanPostProcessor beanPostProcessor = (BeanPostProcessor) aClass.getConstructor().newInstance();
                            beanPostProcessors.add(beanPostProcessor);
                        }else {
                            //表示被Component修饰
                            if (aClass.isAnnotationPresent(Component.class)){

                                //获取bean的名字存入BeanDefinitionMap
                                String beanName = aClass.getAnnotation(Component.class).value();

                                //如果bean未写
                                if ("".equals(beanName)){
                                    beanName = Introspector.decapitalize(aClass.getSimpleName());
                                }

                                //这是我们需要创建的bean
                                System.out.println("修饰的class"+absolutePath);

                                //创建beandefinition
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(aClass);

                                //判断有没有scope注解,没有默认单例
                                if (aClass.isAnnotationPresent(Scope.class)){
                                    String value = aClass.getAnnotation(Scope.class).value();
                                    beanDefinition.setScope(value);
                                }else {
                                    //单例
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName,beanDefinition);

                            }
                        }


                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }

    public Object getBean(String beanName){
        Object bean = null;
        if (!beanDefinitionMap.containsKey(beanName)){
            throw  new NullPointerException();
        }
        //如果有
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

       if ("singleton".equals(beanDefinition.getScope())){
           //单例逻辑
            if (!singletonObject.containsKey(beanName)){
              return createBean(beanName, beanDefinition);
            }
            bean = singletonObject.get(beanName);
           if (bean == null){
               bean =  createBean(beanName, beanDefinition);
           }
           singletonObject.put(beanName,bean);
           return bean;
       }
       //其他
       return createBean(beanName, beanDefinition);
    }


    //创建bean
    private Object createBean(String beanName,BeanDefinition beanDefinition){
        Class aclass = beanDefinition.getType();
        Object instance = null;
        try {
            //通过反射+无参构造创建bean
            instance = aclass.getConstructor().newInstance();

            //进行依赖注入
            //遍历字段属性
            for (Field field : aclass.getDeclaredFields()){
                if (field.isAnnotationPresent(Autowired.class)){
                    field.setAccessible(true);
                    field.set(instance,getBean(field.getName()));
                }
            }

            //实例化接口回调
            if (instance instanceof InitializingBean){
                ((InitializingBean) instance).afterPropertiesSet();
            }

            //BeanPostProcessor
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors){
                beanPostProcessor.postProcessAfterInitializingBean(instance,beanName);
            }


        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return instance;
    }

}
