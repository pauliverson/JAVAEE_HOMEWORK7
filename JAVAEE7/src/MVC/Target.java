package MVC;

import java.lang.reflect.Method;

public class Target {
	public Method method;
	public Object target;
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	
}
