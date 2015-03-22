package throwable.server.handler.kind;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.bean.Kind;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月15日
 */
@At("/kind")
@IocBean
public class KindController {

	@Inject
	private KindServer kindServer;
	
	@At("/addKind")
	public Map<String, Object> addKind(@Param("..") Kind kind){
		if(StringTool.isEmpty(kind.kind_name) || kind.user_id < 1){
			return BackTool.errorInfo("040101", ThrowableConf.errorMsg);
		}
		return kindServer.addKind(kind);
	}
	
	@SuppressWarnings("rawtypes")
	@At("/queryAllKinds")
	public List<Map> findAllKinds() {
		return kindServer.queryAllKinds();
	}
}
