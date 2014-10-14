/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.question;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

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
	public Map<String, Object> addQuestion(){
		return null;
	}
}
