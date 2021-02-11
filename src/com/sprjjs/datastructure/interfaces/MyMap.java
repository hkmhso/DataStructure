package com.sprjjs.datastructure.interfaces;
/**
 * 哈希表接口--增加泛型
 * 和存储结构无关
 * @author Administrator
 *
 */
public interface MyMap<K,V>{
	// 返回哈希表的大小，即数据元素的个数。
	public int size();
	
	// 返回哈希表中key映射的value
	public V get(K key);

	// 添加key-value,返回value
	public V put(K key,V value);
		
	// 如果哈希表为空返回 true，否则返回 false。
	public boolean isEmpty();

	// 判断哈希表是否包含 该key
	public boolean containsKey(K Key);
	
	// 判断哈希表是否包含 该value
	public boolean containsValue(V value);

	// 删除哈希表中key映射的value,并返回之
	public V remove(K Key);

	// 替换哈希表中key映射的value，返回原value元素
	public V replace(K key, V value);

}
