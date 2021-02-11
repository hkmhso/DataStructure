package com.sprjjs.datastructure.test;

import com.sprjjs.datastructure.interfaces.MyList;
import com.sprjjs.datastructure.linetable.BothLinkedTable;
/**
 * 测试双链表
 * @author HKM
 *
 */
public class TestBothLinkedTable {
	public static void main(String[] args) {
		MyList<String> list=new BothLinkedTable<>();
		list.add("123");
		list.add("777");
		list.add(1,"999");
		list.add("123");
		list.add("888");
		System.out.println("删除前的数组:"+list.toString());
		System.out.println("数组是否为空"+list.isEmpty());
		System.out.println("删除前数组长度:"+list.size());
		System.out.println("索引为1的元素:"+list.get(1));
		System.out.println("123第一次出现的位置:"+list.indexOf("123"));
		System.out.println("123最后一次出现的位置:"+list.lastIndexOf("123"));
		System.out.println("数组是否包含777的元素:"+list.contains("777"));
		System.out.println("删除的元素："+list.remove(0));
		System.out.println("删除123的状态："+list.remove("123"));
		System.out.println("删除后的数组:"+list.toString());
		System.out.println("删除后数组长度:"+list.size());
		System.out.println("替换的元素为"+list.replace(1, "000"));
		System.out.println("替换后的数组:"+list.toString());
		System.out.println("将元素插入到另一个元素的后面的状态："+list.addAfter("000","567"));
		System.out.println("将元素插入到另一个元素的后面后的数组:"+list.toString());
		System.out.println("将元素插入到另一个元素的后面后数组长度:"+list.size());
		System.out.println("将元素插入到另一个元素的前面的状态："+list.addBefore("000","111"));
		System.out.println("将元素插入到另一个元素的前面后的数组:"+list.toString());
		System.out.println("将元素插入到另一个元素的前面后数组长度:"+list.size());

	}

}
