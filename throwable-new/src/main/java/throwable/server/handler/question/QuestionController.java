/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.question;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.bean.Question;

/**
 * @author WaterHsu
 *
 */
@At("/question")
@IocBean
public class QuestionController {

	@Inject
	private QuestionServer questionServer;
	
	@At("/addQuestion")
	public Map<String, Object> addQuestion(@Param("..") Question question){
		return null;
	}
	
	@At("/queryQuestion")
	public Map<String, String> queryQuestion(@Param("id") int id){
		System.out.println("throwable-server " + " " + id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Hello", "World");
		map.put("id", "" + id);
		return map;
	}
}
