package com.sprjjs.datastructure.interfaces;

import java.util.Iterator;

/**
 * 迭代器接口
 * @author HKM
 *
 */
public interface MyIterator<E>{
	//是否还有下一个元素
	boolean hasNext();
	//将游标移向下一个元素
	E next();

}
