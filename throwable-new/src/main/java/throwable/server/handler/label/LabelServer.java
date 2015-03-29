/**
 * Studia Seava
 * 
 * created in 2015年3月11日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.label;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import throwable.server.bean.Label;
import throwable.server.enums.LabelType;
import throwable.server.service.LabelService;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu
 *
 */
@IocBean
public class LabelServer {

	@Inject
	private LabelService labelService;
	private static Log log = Logs.getLog(LabelServer.class);
	/**
	 * 添加标签
	 * @param name
	 * @param type
	 * @return
	 */
	public Map<String, Object> addLabel(final int userId, String name, String type) {
		final Label label = new Label();
		label.name = name;
		label.type = LabelType.getValue(type);
		label.time = System.currentTimeMillis();
		try {
		Trans.exec(new Atom() {
			
			@Override
			public void run() {
				//先查询标签是否存在  如果存在  则直接只维护标签和用户的关系
				Label label_n = labelService.queryLabelByName(label.name);
				//如果不存在  则先查如改标签  然后查询标签的id  再维护标签和用户的关系
				if(null == label_n) {
					labelService.addLabel(label);
					label_n = labelService.queryLabelByName(label.name);
				}
				if(StringTool.isEmpty(labelService.queryExists(userId, label_n.id))) {
					labelService.addUserLabel(userId, label_n.id);
				}
			}
		});
		} catch(Exception e) {
			log.error("添加标签出错 ", e);
			return BackTool.errorInfo("070102");
		}
		return BackTool.successInfo();
	}
	
	/**
	 * 查询所有的标签
	 * @return
	 */
	public List<Label> queryAllLabel() {
		List<Label> list = labelService.queryAllLabels();
		return StringTool.isEmpty(list) ? new ArrayList<Label>() : list;
	}
	
	/**
	 * 根据问题id查询标签
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryLabelsByQuestionId(int questionId) {
		List<Map> list = labelService.queryLabelsByQuestionId(questionId);
		return StringTool.isEmpty(list) ? new ArrayList<Map>() : list;
	}
	
	/**
	 * 根据用户id查询标签
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryLabelsByUserId(int userId) {
		List<Map> list = labelService.queryLabelsByUserId(userId);
		return StringTool.isEmpty(list) ? new ArrayList<Map>() : list;
	}
}
