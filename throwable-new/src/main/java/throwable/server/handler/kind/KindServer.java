package throwable.server.handler.kind;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.ThrowableConf;
import throwable.server.bean.Kind;
import throwable.server.service.KindService;
import throwable.server.utils.BackTool;

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
		kindService.insertKind(kind);
		return BackTool.successInfo();
	}
}
