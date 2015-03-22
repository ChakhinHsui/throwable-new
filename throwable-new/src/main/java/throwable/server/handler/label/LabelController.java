/**
 * Studia Seava
 * 
 * created in 2015年3月11日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.label;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import throwable.server.bean.Label;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * 标签处理
 * @author WaterHsu
 *
 */
@At("label")
@IocBean
public class LabelController {

	@Inject
	private LabelServer labelServer;
	
	/**
	 * 增加标签
	 * @param userId  用户id
	 * @param name    标签名
	 * @param type    标签类型 网站公有  用户私有
	 * @return
	 */
	@At("/addLabel")
	public Map<String, Object> addLabel(int userId, String name, String type) {
		if(userId < 1 || StringTool.isEmpty(name) || StringTool.isEmpty(type)) {
			BackTool.errorInfo("070101");
		}
		return labelServer.addLabel(userId, name, type);
	}
	
	/**
	 * 查询所有的label
	 * @return
	 */
	@At("/queryAllLabels")
	public List<Label> queryAllLabel() {
		return labelServer.queryAllLabel();
	}
	
	/**
	 * 根据问题id查询标签信息
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@At("/queryLabelsByQuestionId")
	public List<Map> queryLabelsByQuestionId(int questionId) {
		if(questionId < 1) {
			BackTool.errorInfo("070103");
		}
		return labelServer.queryLabelsByQuestionId(questionId);
	}
	
	/**
	 * 根据用户id查询标签信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@At("/queryLabelsByUserId")
	public List<Map> queryLabelsByUserId(int userId) {
		if(userId < 1) {
			BackTool.errorInfo("070104");
		}
		return labelServer.queryLabelsByUserId(userId);
	}
	
}
