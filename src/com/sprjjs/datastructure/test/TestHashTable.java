package com.sprjjs.datastructure.test;

import java.util.HashMap;

import com.sprjjs.datastructure.hashtable.Hashtable;
import com.sprjjs.datastructure.interfaces.MyMap;

/**
 * 测试哈希表--HashMap
 * @author HKM
 *
 */
public class TestHashTable {
	public static void main(String[] args) {
		MyMap<Integer,Integer> map=new Hashtable<>();
		map.put(1, 1);
		map.put(2, 2);
		map.put(3, 3);
		map.put(4, 4);
		map.put(6, 6);
		map.put(6, 66);
		System.out.println("删除前map集合:"+map);
		System.out.println("删除前map集合大小"+map.size());
		System.out.println("删除前获取map的key为1映射的value:"+map.get(1));
		System.out.println("删除的key为6映射的value:"+map.remove(6));;
		System.out.println("删除后map集合:"+map);
		System.out.println("删除后map集合大小"+map.size());
		System.out.println("删除后获取map的key为1映射的value:"+map.get(1));
		System.out.println("替换key为1前返回的旧value:"+map.replace(1, 1111));;
		System.out.println("替换后map集合:"+map);
		System.out.println("替换后map集合大小"+map.size());
		System.out.println("替换后获取map的key为1映射的value:"+map.get(1));
		System.out.println("是否存在key为1的状态:"+map.containsKey(43));
		System.out.println("是否存在value为1的状态:"+map.containsValue(3));
		
	}

}
class people{
	private String name;
	private int age;
	public people(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		people other = (people) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "people [name=" + name + ", age=" + age + "]";
	}
	
	
}
