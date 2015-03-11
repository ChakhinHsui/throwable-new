package throwable.server.api;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import throwable.server.service.KindService;

/**
 * 分类查询api
 * 
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
@At("kind")
@IocBean
public class KindApi {

	@Inject
	private KindService kindService;
	
	@At("/queryAllKindPart")
	@SuppressWarnings("rawtypes")
	public List<Map> queryAllKind() {
		return kindService.queryAllKindsPart();
	}
}
