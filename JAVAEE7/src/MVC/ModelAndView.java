package MVC;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModelAndView {
	Map<String,Object> map=new HashMap<String,Object>();
	String view_Name;
	
	public String getView_Name() {
		return view_Name;
	}
	public void setView_Name(String view_Name) {
		this.view_Name = view_Name;
	}
	public Object getMap(String str){
		return map.get(str);
	}
	public void addObject(String str,Object map){
		this.map.put(str, map);
	}
	public Set<String> getParameters(){
		return map.keySet();
	}
}
