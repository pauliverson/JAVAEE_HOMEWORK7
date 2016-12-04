package MVC;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DispathcherServlet
 */
@WebServlet("/DispathcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public static HashMap<String,Target> map=new HashMap<>();
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(){
    	String tomcatPath=System.getProperty("catalina.home");
    	String porjectName="JAVAEE7";//本次项目的名字
    	File dir=new File(tomcatPath+"/webapps/"+porjectName+"/WEB-INF/classes/test");
    	for(File f:dir.listFiles()){
			String className=f.getName();
    		Class c=null;
    		String packageName="test.";
    		System.out.println(className);
    		try {
				c=Class.forName(packageName+className.substring(0,className.length()-6));
				if(c.isAnnotationPresent(Controller.class)){
					Object object=c.newInstance();
					for(Method method:c.getDeclaredMethods()){
						if(method.isAnnotationPresent(RequestMapping.class)){
							Target target=new Target();
							target.setMethod(method);
							target.setTarget(object);
							RequestMapping rm=(RequestMapping)method.getAnnotation(RequestMapping.class);
							map.put(rm.value(), target);
						}
					}
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    
    	}
    }
	protected void service(HttpServletRequest request, HttpServletResponse response){
		Target target=map.get(request.getServletPath());
		if(target==null){
			return;
		}
		ModelAndView mav=new ModelAndView();
		Map map=request.getParameterMap();
		for(String key:(Set<String>)map.keySet()){
			String[] values = (String[]) map.get(key);
			for(String value:values){
				mav.addObject(key, value);
			}
		}
		Object result=null;
		try {
			result=target.getMethod().invoke(target.getTarget(), mav);
			if(result instanceof ModelAndView){
				mav=(ModelAndView) result;
				for(String string:mav.getParameters()){
					
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
