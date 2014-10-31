
package throwable.server;

import java.util.Map;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.junit.Before;

import throwable.server.framework.client.ThriftClientFactory;
import throwable.server.framework.client.ThriftConnect;
import throwable.server.framework.rpc.ResultMsg;



public class AbstractThritfController {

//	private String host = "192.168.1.226";
//	private int port = 9169;
	private String host = "localhost";
	private int port = 9169;

	private static GenericObjectPool<ThriftConnect> pool;

	@Before
	public void setUp() throws Exception {
		ThriftClientFactory factory = new ThriftClientFactory(host, port, 10 * 1000);
		pool = new GenericObjectPool<ThriftConnect>(factory);
		pool.setMinIdle(5);
		pool.setMaxIdle(50);
		pool.setMaxActive(100);
	}

	public ResultMsg call(String rpid, String funCode, Map<String, String> paramMap) throws org.apache.thrift.TException {
		ThriftConnect conn = null;
		try {
			conn = pool.borrowObject();
			ResultMsg ret = conn.getClient().call(rpid, funCode, paramMap);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pool.returnObject(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
