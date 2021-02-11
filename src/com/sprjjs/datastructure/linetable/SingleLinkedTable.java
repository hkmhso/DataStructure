package com.sprjjs.datastructure.linetable;

import com.sprjjs.datastructure.interfaces.MyIterator;
import com.sprjjs.datastructure.interfaces.MyList;
import com.sprjjs.datastructure.pojo.MyException;

/**
 * 2.链表--链式存储结构
 * 		单向链表--增加泛型
 * @author HKM
 *
 */
public class SingleLinkedTable<E> implements MyList<E>{
	//头结点，不存放任何的数据对象，用来简便编程
	private Node<E> head=new Node<>();
	//结点元素
	private int size;
	
	public SingleLinkedTable() {
		super();
	}
	
	/**
	 * 结点:一个结点（增加泛型）包括两个部分
	 * 		1.数据域
	 * 		2.指针域（指向下一个结点的地址）
	 * @author HKM
	 *
	 */
	private static class Node<E> {
		//数据域
		E data;
		//指针域
		Node<E> next;
		Node(E data) {
			super();
			this.data = data;
		}
		Node() {
			super();
		}
		@Override
		public String toString() {
			return "Node [data=" + data + ", next=" + next + "]";
		}
		
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public E get(int i) {
		//PS:因为head结点不存任何的数据元素，所以需要将指针移向第一个结点
		Node<E> p=head;
		for(int j=0;j<=i;j++){
			//PS:将指针移向下一个结点
			p=p.next;
		}
		return p.data;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public boolean contains(E e) {
		if(e!=null&&indexOf(e)!=-1){
			return true;
		}
		return false;
	}

	@Override
	public int indexOf(E e) {
		//没存在该元素，则返回-1
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//获取索引对应的结点
			E data=get(i);
			//比较内容，不能用==,否则比较的是地址
			if(data.equals(e)){
				index=i;
				break;
			}
		}
		return index;
	}

	//插入校验
	private void rangeCheckForAdd(int ...i){
		if(i!=null&&i.length>0){
			if(i[0]<0||i[0]>size){
				throw new MyException("数组越界--插入"+i[0]);
			}
		}
		
	}
	
	//删除，替换..校验
	private void rangeCheck(int ...i){
		if(i!=null&&i.length>0){
			if(i[0]<0||i[0]>=size){
				throw new MyException("数组越界--删除或替换"+i[0]);
			}
		}
	}

	@Override
	public int lastIndexOf(E e) {
		//没存在该元素，则返回-1
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//获取索引对应的结点
			Object data=get(i);
			//比较内容，不能用==,否则比较的是地址
			if(data.equals(e)){
				index=i;
			}
		}
		return index;
	}

	//插入一个结点,步骤如下：
	@Override
	public void add(int i, E e) {
		rangeCheckForAdd(i);
		//[1]新建一个结点
		Node<E> newNode=new Node<>(e);
		//在[2]步骤前，需要先找出原来索引为i结点的前一个结点，从头结点开始找
		Node<E> p=head;
		for(int j=0;j<i;j++){
			//PS:将指针移向下一个结点
			p=p.next;
		}
		//[2]指明新结点的直接后继结点
		newNode.next=p.next;
		//[3]更改原来索引为i结点的前一个结点(也就是索引为i-1的结点)的直接后继结点
		p.next=newNode;
		//[4]结点个数+1
		size++;
	}

	@Override
	public void add(E e) {
		add(size,e);
	}

	@Override
	public boolean addBefore(E obj, E e) {
		//[1]获取obj的索引
		int i=indexOf(obj);
		if(obj!=null&&i!=-1){
			//[2]在i处插入e节点
			add(i,e);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAfter(E obj, E e) {
		//[1]获取obj的索引
		int i=indexOf(obj);
		if(obj!=null&&i!=-1){
			//[2]在i+1处插入e节点
			add(i+1,e);
			return true;
		}
		return false;
	}

	@Override
	public E remove(int i) {
		rangeCheck(i);
		//[1].找到i-1索引的结点，从头结点开始找
		Node<E> p=head;
		for(int j=0;j<i;j++){
			//PS:将指针移向下一个结点
			p=p.next;
		}
		//[2]记录删除的结点
		Node<E> removeNode=p.next;
		//[3]更改原来索引为i结点的前一个结点(也就是索引为i-1的结点)的直接后继结点
		p.next=p.next.next;
		//[4]结点个数-1
		size--;
		return removeNode.data;
	}

	@Override
	public boolean remove(E e) {
		//[1]首先要判断该元素是否存在，存在返回true，否则false
		int i=indexOf(e);
		if(i!=-1){
			remove(i);
			return true;
		}
		return false;
	}

	@Override
	public E replace(int i, E e) {
		rangeCheck(i);
		/*第一种方式：相当于删除i节点，再往i插入新的节点
		E data=remove(i);
		add(i,e);*/
		//第二种方式
		//[1]先得到对应索引处的结点元素
		Node<E> node = getNode(i);
		//[2]保存结点旧的数据
		E oldVal = node.data;
		//[3]替换成新的数据
		node.data=e;
		return oldVal;
		
	}
	
	/**
	 * 获取对应索引处的结点对象
	 * @param i 索引处
	 * @return 结点对象
	 */
	private Node<E> getNode(int i){
		//PS:因为head结点不存任何的数据元素，所以需要将指针移向第一个结点
		Node<E> p=head;
		for(int j=0;j<=i;j++){
			//PS:将指针移向下一个结点
			p=p.next;
		}
		return p;
	}
	
	//返回数组的内容,例：[1,2,3]
	@Override
	public String toString() {
		if(size==0){
			return "[]";
		}
		StringBuilder builder=new StringBuilder("[");
		//PS:因为head结点不存任何的数据元素，所以需要将指针移向第一个结点
		Node<E> p=head.next;
		for(int i=0;i<=size-1;i++){
			if(i==size-1){
				builder.append(p.data);
			}
			else{
				builder.append(p.data+",");
			}
			//PS:将指针移向下一个结点
			p=p.next;
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public MyIterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
		

}
