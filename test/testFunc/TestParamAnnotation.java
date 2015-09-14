package testFunc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.zzh.robinmvc.annotation.RequestParam;

import controller.TestController;

public class TestParamAnnotation {

	@Test
	public void testParamAnnotation() throws NoSuchMethodException, SecurityException 
	{
		Class tcClazz = TestController.class;
		@SuppressWarnings("unchecked")
		Method method = tcClazz.getMethod("test1",new Class[]{String.class, int.class});
		Class<?>[] parameterClazz = method.getParameterTypes();
		Annotation[][] an = method.getParameterAnnotations();
		
		
		if(an.length == parameterClazz.length)
		{	
			for(int i=0;i<parameterClazz.length;i++)
			{
				RequestParam rp = (RequestParam)an[i][0];
				System.out.println(rp.value());
				
			}
		}
		
		
		
	}
	
	
	
}
