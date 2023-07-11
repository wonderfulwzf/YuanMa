package sping;

//bean的定义
public class BeanDefinition {

    private Class type;

    private String scope;

    private boolean islazy;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isIslazy() {
        return islazy;
    }

    public void setIslazy(boolean islazy) {
        this.islazy = islazy;
    }
}
