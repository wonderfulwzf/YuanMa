package sping;

import wzf.service.UserService;

import java.io.File;
import java.net.URL;

public class WzfApplicationContext {


    private final Class configClass;


    public WzfApplicationContext(Class configClass) {
        this.configClass = configClass;

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
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    //表示被Component修饰
                    if (aClass.isAnnotationPresent(Component.class)){
                        //这是我们需要创建的bean
                        System.out.println("修饰的class"+absolutePath);
                    }




                }

            }
        }
    }

    public Object getBean(String beanName){
        return new UserService();
    }

}
