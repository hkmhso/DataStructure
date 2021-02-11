package com.sprjjs.datastructure.interfaces;

/**
 * 线性表接口--增加泛型
 * 和存储结构无关
 * @author Administrator
 *
 */
public interface MyList<E> {
	// 返回线性表的大小，即数据元素的个数。
	int size();
	
	// 返回线性表中序号为 i 的数据元素
	E get(int i);

	// 如果线性表为空返回 true，否则返回 false。
	boolean isEmpty();

	// 判断线性表是否包含数据元素 e
	boolean contains(E e);

	// 返回数据元素 e第一次 在线性表中的序号
	int indexOf(E e);

	// 返回数据元素 e最后一次 在线性表中的序号
	int lastIndexOf(E e);
	
	// 将数据元素 e 插入到线性表中 i 号位置
	void add(int i, E e);
	
	// 将数据元素 e 插入到线性表末尾
	void add(E e);

	// 将数据元素 e 插入到元素 obj 之前
	boolean addBefore(E obj, E e);

	// 将数据元素 e 插入到元素 obj 之后
	boolean addAfter(E obj, E e);

	// 删除线性表中序号为 i 的元素,并返回之
	E remove(int i);

	// 删除线性表中第一个与 e 相同的元素
	boolean remove(E e);

	// 替换线性表中序号为 i 的数据元素为 e，返回原数据元素
	E replace(int i, E e);
	
	//获取迭代器对象
	MyIterator<E> iterator();

	
}
