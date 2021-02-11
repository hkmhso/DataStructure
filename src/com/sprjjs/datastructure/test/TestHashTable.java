package com.sprjjs.datastructure.test;

import java.util.HashMap;

import com.sprjjs.datastructure.hashtable.Hashtable;
import com.sprjjs.datastructure.interfaces.MyMap;

/**
 * ���Թ�ϣ��--HashMap
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
		System.out.println("ɾ��ǰmap����:"+map);
		System.out.println("ɾ��ǰmap���ϴ�С"+map.size());
		System.out.println("ɾ��ǰ��ȡmap��keyΪ1ӳ���value:"+map.get(1));
		System.out.println("ɾ����keyΪ6ӳ���value:"+map.remove(6));;
		System.out.println("ɾ����map����:"+map);
		System.out.println("ɾ����map���ϴ�С"+map.size());
		System.out.println("ɾ�����ȡmap��keyΪ1ӳ���value:"+map.get(1));
		System.out.println("�滻keyΪ1ǰ���صľ�value:"+map.replace(1, 1111));;
		System.out.println("�滻��map����:"+map);
		System.out.println("�滻��map���ϴ�С"+map.size());
		System.out.println("�滻���ȡmap��keyΪ1ӳ���value:"+map.get(1));
		System.out.println("�Ƿ����keyΪ1��״̬:"+map.containsKey(43));
		System.out.println("�Ƿ����valueΪ1��״̬:"+map.containsValue(3));
		
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
