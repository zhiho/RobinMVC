package org.zzh.robinmvc.switcher;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 基本数据类型转换工具类
 * @author zhangzhihao
 *
 */

public class TypeSwitcher {

	private final Log log = LogFactory.getLog(getClass());
	
	private Map<Class<?>, Switcher<?>> map = new HashMap<Class<?>,Switcher<?>>();
	
	public TypeSwitcher(){
		loadParameters();
	}
	
	//加入8种基本类型的转换器
	private void loadParameters(){
		Switcher<?> switcher = null;
		switcher =  new TypeSwitcherSupport.BooleanSwitcher();
        map.put(boolean.class, switcher);
        map.put(Boolean.class, switcher);

        switcher = new TypeSwitcherSupport.CharacterSwitcher();
        map.put(char.class, switcher);
        map.put(Character.class, switcher);

        switcher = new TypeSwitcherSupport.ByteSwitcher();
        map.put(byte.class, switcher);
        map.put(Byte.class, switcher);

        switcher = new TypeSwitcherSupport.ShortSwitcher();
        map.put(short.class, switcher);
        map.put(Short.class, switcher);

        switcher = new TypeSwitcherSupport.IntegerSwitcher();
        map.put(int.class, switcher);
        map.put(Integer.class, switcher);

        switcher = new TypeSwitcherSupport.LongSwitcher();
        map.put(long.class, switcher);
        map.put(Long.class, switcher);

        switcher = new TypeSwitcherSupport.FloatSwitcher();
        map.put(float.class, switcher);
        map.put(Float.class, switcher);

        switcher = new TypeSwitcherSupport.DoubleSwitcher();
        map.put(double.class, switcher);
        map.put(Double.class, switcher);		
		
	}
	
	/**
	 * 转换基本数据类型为指定的class类型
	 * @param clazz
	 * @param s
	 * @return
	 * @throws Exception 
	 */
	public Object switcher(Class<?> clazz, String s) throws Exception{
		Switcher<?> switcher = map.get(clazz);
		return switcher.switcher(s);
	}
	
	/**
	 * 判断方法参数是否为基本类型或字符串
	 * @param clazz
	 * @return
	 */
	public boolean isBaseTypeOrString(Class<?> clazz){
		return clazz.equals(String.class)||map.containsKey(clazz);
	}

	
	
	
	
	
}
