package com.sprjjs.datastructure.hashtable;

import java.util.*;

import javax.swing.tree.TreeNode;

import com.sprjjs.datastructure.interfaces.MyMap;

/**
 * 1.哈希表(数组加链表)--哈希存储结构--模拟HashMap
 * 		增加泛型
 * @author HKM
 *
 */
public class Hashtable<K,V> implements MyMap<K, V>{
	//默认数组长度,注意：必须为2的整数幂
	private static final int DEFAULT_INITIAL_CAPACITY = 1<<2;//DEFAULT_INITIAL_CAPACITY==16
	//位桶数组,装结点元素,一个索引位置可以是一个链表
	private Node<K, V>[] tables;
	//结点数量
	private int size;
	//记录位桶数组的剩余容量
	private int residueCap;

	public Hashtable() {
		//如果没有指定长度，则使用默认数组长度
		this(DEFAULT_INITIAL_CAPACITY);
	}
	public Hashtable(int initialCapacity) {
		//给数组分配空间
		if(initialCapacity<0){
			throw new RuntimeException("数组长度不合法");
		}else{
			tables=new Node[initialCapacity];
			residueCap=tables.length;
		}
		
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public V get(K key) {
		//[1]通过key的hash值来定位到存放数组的位置
		int hash = myHash(key);
		//[2]根据key获取结点
		Node<K, V> node = getNode(hash,key);
		//有该key，返回对应映射的value
		if(node!=null){
			return node.value;
		}
		//如果没有该key，则返回null
		return null;
	}
	
	//根据hash值和key返回Node结点
	private Node<K, V> getNode(int hash,K key){
		//[1]通过key的hash值来获取到对应位置链表的第一个结点
		Node<K, V> currNode=tables[hash];
		//[2]根据equlas()比较key
		if(currNode!=null){
			//是否是对应索引处的链表的最后一个结点
			while(currNode!=null){
				//[3]有该key，返回对应的Node结点
				if(currNode.key.equals(key)){
					return currNode;
				}
				//[3]将指针移向下一个Node
				currNode=currNode.next;
			}
		}
		//[3]没有该key，返回null
		return null;
	}

	@Override
	public V put(K key, V value) {
		//[1]通过key的hash值来定位到存放数组的位置
		int hash = myHash(key);
		//[2]需要考虑到扩容
		//何时扩容？当位桶数组剩余容量==0或插入的key对应的hash值>位桶数组长度-1
		if(hash>tables.length-1||residueCap==0){
			//扩容
			resize();
		}
		System.out.println("hash码为"+hash+"    位桶数组大小"+tables.length);
		putVal(hash,key,value);
		return value;
	}
	
	//扩容步骤：
	private void resize(){
		//[1]重新定义一个按照规律扩容后的位桶数组,且长度必须是2的整数幂,<<1:表示扩大一倍
		Node<K, V>[] newTables=new Node[tables.length<<1];
		System.out.println("扩容后的数组长度为"+newTables.length);
		//[2]将旧位桶数组的值拷贝到新的位桶数组中
		Node<K, V> oldNode=null;
		Node<K, V> newNode=null;
		for(int i=0;i<=tables.length-1;i++){
			//[2.1]获取旧位桶数组对应索引处的链表
			oldNode= tables[i];
			//[2.2]将索引处的链表的第一个结点赋给新位桶数组
			newTables[i]=oldNode;
			//[2.3]对应索引处的链表正在遍历的下一个结点
			newNode=newTables[i];
			//[2.4]判断旧位桶数组对应索引处的链表是否还有下一个
			while(oldNode!=null){
				//[2.5]将旧位桶数组对应索引处的链表指针移向下一个Node
				oldNode=oldNode.next;
				/*[2.6]将新位桶数组对应索引处的链表的上一个结点的next指向旧位桶数组对应索引处的链表的下一个结点,
				从而形成链表
				*/
				newNode.next=oldNode;
				//将新位桶数组对应索引处的链表指针移向下一个Node
				newNode=oldNode;
			}
		}
		//[3]将指针指向新位桶数组
		tables=newTables;
	}
		
	//添加结点
	private void putVal(int hash, K key, V value) {
		/*[2]判断该位置是否已经有Node了(即该索引上的链表不为空)，如果有：则根据equals()判断是否存在key重复的情况，
		 否则直接加到链表中，如果key存在重复：则覆盖key映射的旧value,否则追加到链表的末尾
		*/
		Node<K, V> currNode = tables[hash];
		//[3]新建一个结点
		Node<K, V> newNode=new Node<>(hash,key,value,null);
		//定义key是否重复的标志位
		boolean isKeyRepeated=false;
		//正在遍历的最后一个结点
		Node<K, V> lastNode=null;
		if(currNode==null){
			//[2.1]将新Node加到链表中
			tables[hash]=newNode;
			//数组的容量-1
			residueCap--;
			System.out.println("位桶数组剩余容量"+residueCap);
		}else{
			//[2.2]判断是否存在key重复的情况,需要逐一遍历链表
			while(currNode!=null){
				//[2.3]如果相同，则覆盖key映射的旧value
				if(currNode.key.equals(key)){
					currNode.value=value;
					isKeyRepeated=true;
					return;
				}
				//[2.4]记录正在遍历的最后一个结点
				lastNode=currNode;
				//[2.5]将指针移向下一个Node
				currNode=currNode.next;
			}
			//如果不存在key重复的情况，则将新节点追加到链表末尾
			if(!isKeyRepeated){
				//[4]将添加新结点之前的链表最后一个结点的next指向新结点
				lastNode.next=newNode;
			}
		}
		//[5]结点个数+1
		size++;
	}
	/**
	 * 获取hash值
	 * @param key 键名
	 * @return
	 */
	public int myHash(K key){
		//计算hash规则:hashCode值%位桶数组长度-1
		return (key.hashCode())%(tables.length-1);
	}
	
	@Override
	public boolean isEmpty() {
		return size==0?true:false;
	}

	@Override
	public boolean containsKey(K key) {
		//[1]判断是否存在该key对应的结点,如果有，则返回true，否则返回false
		Node<K, V> node = getNode(myHash(key), key);
		return node==null?false:true;
	}

	@Override
	public boolean containsValue(V value) {
		//[1]遍历位桶数组,是否存在该key对应的结点,如果有，则返回true，否则返回false
		Node<K, V> currNode =null;
		//遍历哈希表
		for(int i=0;i<=tables.length-1;i++){
			//[1]获取对应索引处的链表
			currNode= tables[i];
			//[2]判断是否还有下一个
			while(currNode!=null){
				//根据equals判断value是否相同
				if(currNode.value.equals(value)){
					return true;
				}
				//[3]将指针移向下一个Node
				currNode=currNode.next;
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public V remove(K key) {
		//[1]根据key的hash值和key获取结点
		Node<K, V> node = getNode(myHash(key),key);
		//[2]存在该键，则返回key映射的value
		if(node!=null){
			//[2]移除该结点
			removeNode(node);
			return node.value;
		}
		//[3]不存在，返回null
		return null;
		
	}

	/**
	 * 删除结点的情况--要考虑释放删除节点的空间,防止内存泄漏
	 * 	1.是第一个结点，且该索引处链表只存在一个结点
	 * 		操作：[1]将位桶数组该索引处的值置为null
	 * 			 
	 *  2.是第一个结点，且该索引处链表存在多个结点
	 *  	操作：[1]将当前结点的后驱结点变成链表的第一个结点，即tables[i]=后驱结点
	 *   		 [2]将当前结点置为null
	 *  
	 *  3.是中间的结点
	 *  	操作：[1]将当前结点的前驱结点的next指向当前结点的后驱结点
	 *  		 [2]将当前结点置为null
	 *  
	 *  4.是最后一个结点
	 *  	操作：[1]将当前结点的前驱结点的next置为null
	 *  		 [2]将当前结点置为null
	 */
	private void removeNode(Node<K, V> node) {
		//索引处链表的第一个结点
		Node<K, V> firstNode = tables[node.hash];
		//[2]获取当前结点的后继结点,以便判断删除的结点位置
		Node<K, V> nextNode = node.next;
		//[3]判断删除节点的位置
		//[3.1]删除的是第一个结点,且该索引处的链表只存在一个结点,或是最后一个结点
		if(nextNode==null){
			//[3.1.1]删除的是第一个结点,且该索引处的链表只存在一个结点
			if(firstNode==node){
				//[3.1.1.1]将位桶数组该索引处的值置为null
				tables[node.hash]=null;
			}
			//[3.1.2]删除的是最后一个结点
			else{
				//[3.1.2.1]将当前结点的前驱结点的next置为null
				getBeforeNode(node).next=nextNode;
				//[3.1.2.2]将当前结点置为null
				node=null;
			}
		}
		//[3.2]删除是第一个结点，且该索引处链表存在多个结点,或是中间的结点
		else{
			//[3.2.1]删除的是第一个结点,且该索引处链表存在多个结点
			if(firstNode==node){
				//[3.2.1.1]将当前结点的后驱结点变成链表的第一个结点，即tables[i]=后驱结点
				tables[node.hash]=getAfterNode(node);
				//[3.2.1.2]将当前结点置为null
				node=null;
			}
			//[3.2.2]删除的是最后一个结点
			else{
				//[3.2.2.1]将当前结点的前驱结点的next指向当前结点的后驱结点
				getBeforeNode(node).next=getAfterNode(node);
				//[3.2.2.2]将当前结点置为null
				node=null;
			}
		}
		//[5]结点个数—1
		size--;
	}
	
	/*  获取当前结点的上一个结点
	 * 如果是索引处链表第一个结点，则返回null
	 */
	private Node<K, V> getBeforeNode(Node<K, V> node){
		//索引处链表当前指向的结点
		Node<K, V> currNode = tables[node.hash];
		//存在链表
		while(currNode!=null){
			//判断当前结点的next是否指向指定的结点,存在即返回上一个结点，否则返回null
			if(currNode.next==node){
				return currNode;
			}
			currNode=currNode.next;
		}
		//如果是索引处链表第一个结点，则返回null
		return null;
		
	}
	
	/*
	 * 获取当前结点的下一个结点
	 * 如果是索引处链表最一个结点，则返回null
	 */
	private Node<K, V> getAfterNode(Node<K, V> node){
		//索引处链表当前指向的结点
		Node<K, V> currNode = tables[node.hash];
		//存在链表
		while(currNode!=null&&node!=null){
			//判断当前结点的next是否指向指定的结点,存在即返回上一个结点，否则返回null
			if(node.next==currNode){
				return currNode;
			}
			currNode=currNode.next;
		}
		//如果是索引处链表最后一个结点，则返回null
		return null;
	}
	
	@Override
	public V replace(K key, V value) {
		//[1]根据key的hash值和key找到结点
		Node<K, V> node = getNode(myHash(key),key);
		//[2]记录就值，以便返回
		V oldVal=node.value;
		//[2]将旧值替换成新值
		node.value=value;
		return oldVal;
	}
	
	//打印格式{key=value,key=value,....}
	@Override
	public String toString() {
		if(size==0){
			return "{}";
		}
		StringBuffer buffer=new StringBuffer("{");
		Node<K, V> currNode =null;
		//遍历哈希表
		for(int i=0;i<=tables.length-1;i++){
			//[1]获取对应索引处的链表
			currNode= tables[i];
			//[2]判断是否还有下一个
			while(currNode!=null){
				buffer.append(currNode.key+"="+currNode.value+",");
				//[3]将指针移向下一个Node
				currNode=currNode.next;
			}
		}
		buffer.setCharAt(buffer.length()-1,'}');
		return buffer.toString();
	}
	/**
	 * 结点:一个结点（增加泛型）包括四个部分
	 * 		1.hash值=key的hashcode码%位桶数组长度-1
	 * 		2.key
	 * 		3.value
	 *      3.下一个结点指针域 nxet
	 * @author HKM
	 *
	 */
	private static class Node<K,V> {
		//key
		K key;
		//value
		V value;
		//hash值
		int hash;
		//下一个结点指针域
		Node<K,V> next;
		public Node( int hash,K key, V value, Node<K, V> next) {
			super();
			this.key = key;
			this.value = value;
			this.hash = hash;
			this.next = next;
		}
		public Node() {
			super();
		}
		public Node(int hash,K key, V value) {
			super();
			this.key = key;
			this.value = value;
			this.hash = hash;
		}
		
	}

}
