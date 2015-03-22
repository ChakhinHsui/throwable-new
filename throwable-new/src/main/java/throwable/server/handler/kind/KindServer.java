package throwable.server.handler.kind;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.ThrowableConf;
import throwable.server.bean.Kind;
import throwable.server.service.KindService;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月15日
 */
@IocBean
public class KindServer {

	@Inject
	private KindService kindService;
	
	public Map<String, Object> addKind(Kind kind){
		Kind kind_f = kindService.queryKindByKindName(kind.kind_name);
		if(kind_f != null){
			return BackTool.errorInfo("040102", ThrowableConf.errorMsg);
		}
		kind.time = System.currentTimeMillis();
		kindService.insertKind(kind);
		return BackTool.successInfo();
	}
	
	/**
	 * 查询所有分类的部分信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryAllKinds() {
		List<Map> list = kindService.queryAllKindsPart();
		return StringTool.isEmpty(list) ? new ArrayList<Map>() : list;
	}
}
