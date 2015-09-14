package testFunc;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

public class TestJSONObject {

	//private Map map = new HashMap();
	
	@Test
	public void test()
	{
		Map map = new HashMap();
		map.put("name", "tom");
		JSONObject json =  JSONObject.fromObject(map);
		String result = json.toString();
		System.out.println(result);
	}
	
}
