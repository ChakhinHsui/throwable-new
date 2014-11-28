package throwable.server.service;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import junit.framework.TestCase;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月28日
 */
public class SSDBHandlerTest extends TestCase {

	public void testSet() throws Exception{
		Ioc ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader", "application.js", "*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "throwable.server"));
		System.out.println("Hello");
		SsdbHandler ssdbHandler = ioc.get(SsdbHandler.class, "ssdbHandler");
		System.out.println("Hello");
		System.out.println(ssdbHandler);
		System.out.println(ssdbHandler.set("emai", "447"));
		System.out.println(ssdbHandler.get("emai"));
	}
}
