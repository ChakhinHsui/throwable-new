/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.answer;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

/**
 * @author WaterHsu
 *
 */
@At("/answer")
@IocBean
public class AnswerController {

	@Inject
	private AnswerServer answerServer;
	
	public Map<String, Object> addAnswer(){
		return null;
	}
}
