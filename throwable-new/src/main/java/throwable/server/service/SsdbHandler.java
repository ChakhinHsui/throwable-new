package throwable.server.service;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.framework.cache.ssdb.SsdbMasterSlavePool;

/**
 * ssdb处理类 
 * @author WaterHsu@xiu8.com
 * @version 2014年11月28日
 */
@IocBean
public class SsdbHandler {
	
	@Inject
	private SsdbMasterSlavePool ssdbMasterSlavePool;
	
	/**
	 * key-value  set 设置值  
	 * @param key      
	 * @param value
	 * @return  操作成功返回true
	 */
	public boolean set(final String key, final String value) {
		return ssdbMasterSlavePool.getSsdb().set(key, value).check().ok();
	} 
	
	/**
	 * key-value  get  获取值
	 * @param key
	 * @return  
	 */
	public String get(final String key) {
		return ssdbMasterSlavePool.getSsdb().get(key).check().asString();
	}
	
	/**
	 * key-value setx 设置值和过期时间
	 * @param key  
	 * @param value
	 * @param time  过期时间
	 * @return
	 */
	public boolean setx(final String key, final String value, final int time){
		return ssdbMasterSlavePool.getSsdb().setx(key, value, time).check().ok();
	}
	
	/**
	 * key-value  删除一个key
	 * @param key
	 */
	public void del(final String key) {
		ssdbMasterSlavePool.getSsdb().del(key);
	}
	
	/**
	 * key-value  自增
	 * @param key   key值
	 * @param value 增加的量
	 * @return    返回增加后的值
	 */
	public long incr(final String key, int value) {
		return ssdbMasterSlavePool.getSsdb().incr(key, value).check().asLong();
	}
	
	/**
	 * key-value  自减
	 * @param key    key值
	 * @param value  减少的量
	 * @return       返回减少后的值
	 */
	public long decr(final String key, int value) {
		return ssdbMasterSlavePool.getSsdb().decr(key, value).check().asLong();
	}
	
	/**
	 * key-value  给key设置过期时间
	 * @param key    key值
	 * @param time   过期的时间
	 * @return   操作成功返回true
	 */
	public boolean expire(final String key, final int time) {
		return ssdbMasterSlavePool.getSsdb().expire(key, time).check().ok();
	}
	
	/**
	 * key-value 判断是不是存在这个key
	 * @param key
	 * @return  存在返回1  不存在返回0  
	 */
	public boolean exists(final String key) {
		if(ssdbMasterSlavePool.getSsdb().exists(key).check().asInt() == 1) {
			return true;
		}
		if(ssdbMasterSlavePool.getSsdb().exists(key).check().asInt() == 0) {
			return false;
		}
		return false;
	}
	
	/**
	 * key-value 查询加入时间的可以 还剩下多少时间
	 * @param key
	 * @return  返回剩下的时间
	 */ 
	public long ttl(final String key) {
		return ssdbMasterSlavePool.getSsdb().ttl(key).check().asLong();
	}
	
	/**
	 * key-map   hset 
	 * @param key    key值
	 * @param field  字段
	 * @param value  字段值
	 * @return   操作成功返回true
	 */
	public boolean hset(final String key, final String field, final String value) {
		return ssdbMasterSlavePool.getSsdb().hset(key, field, value).check().ok();
	}
	
	/**
	 * key-map  hget 取值
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(final String key, final String field) {
		return ssdbMasterSlavePool.getSsdb().hget(key, field).check().asString();
	}
	
	/**
	 * key-map  删除字段
	 * @param key    key值
	 * @param field  字段名
	 */
	public void hdel(final String key, final String field) {
		ssdbMasterSlavePool.getSsdb().hdel(key, field).check();
	}
	
	/**
	 * key-map   自增
	 * @param key    key值
	 * @param field  字段名
	 * @param value  增量
	 * @return   返回自增后的值
	 */
	public int hincr(final String key, final String field, final int value) {
		return ssdbMasterSlavePool.getSsdb().hincr(key, field, value).check().asInt();
	}
	
	/**
	 * key-map   自减
	 * @param key      key值
	 * @param field    字段名
	 * @param value    减量
	 * @return
	 */
	public int hdecr(final String key, final String field, final int value) {
		return ssdbMasterSlavePool.getSsdb().hdecr(key, field, value).check().asInt();
	}
	
	/**
	 * key-map   获得key的所有<key, value>
	 * @param key   key值
	 * @return
	 */
	public Map<String, Object> hgetall(final String key) {
		return ssdbMasterSlavePool.getSsdb().hgetall(key).check().map();
	}
	/**
	 * key-map    获得集合的大小
	 * @param key key值
	 * @return    返回集合的大小
	 */
	public int hsize(final String key) {
		return ssdbMasterSlavePool.getSsdb().hsize(key).check().asInt();
	}
	
	/**
	 * key-map 同时设置多个值
	 * @param key
	 * @param values
	 */
	public void multi_hset(final String key, final Map<String, Object> values) {
		ssdbMasterSlavePool.getSsdb().multi_hset(key, values).check();
	} 
	
	/**
	 * key-map  同时取多个值
	 * @param key
	 * @param fields   多个字段  不固定
	 * @return
	 */
	public Map<String, Object> multi_hget(final String key, Object...fields) {
		return ssdbMasterSlavePool.getSsdb().multi_hget(key, fields).check().map();
	}
	
	/**
	 * zset  设值
	 * @param key
	 * @param field
	 * @param score
	 */
	public void zset(final String key, final String field, final long score) {
		ssdbMasterSlavePool.getSsdb().zset(key, field, score).check();
	}
	
	/**
	 * zset  取值
	 * @param key
	 * @param field
	 * @return
	 */
	public long zget(final String key, final String field) {
		return ssdbMasterSlavePool.getSsdb().zget(key, field).check().asLong();
	}
	
	/**
	 * zset  自增
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long zincr(final String key, final String field, final int value) {
		return ssdbMasterSlavePool.getSsdb().zincr(key, field, value).check().asLong();
	}
	
	/**
	 * zset  自减
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long zdecr(final String key, final String field, final int value) {
		return ssdbMasterSlavePool.getSsdb().zdecr(key, field, value).check().asLong();
	}
}
