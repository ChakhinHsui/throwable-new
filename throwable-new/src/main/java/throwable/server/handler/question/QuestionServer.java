/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.question;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;

import throwable.server.bean.Question;
import throwable.server.service.QuestionService;

/**
 * @author WaterHsu
 *
 */
public class QuestionServer {

	@Inject
	private QuestionService questionService;
	
	public List<Map> queryAllQuestions(){
		return null;
	}
}
