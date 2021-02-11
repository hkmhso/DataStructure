package com.sprjjs.datastructure.hashtable;

import java.util.*;

import javax.swing.tree.TreeNode;

import com.sprjjs.datastructure.interfaces.MyMap;

/**
 * 1.��ϣ��(���������)--��ϣ�洢�ṹ--ģ��HashMap
 * 		���ӷ���
 * @author HKM
 *
 */
public class Hashtable<K,V> implements MyMap<K, V>{
	//Ĭ�����鳤��,ע�⣺����Ϊ2��������
	private static final int DEFAULT_INITIAL_CAPACITY = 1<<2;//DEFAULT_INITIAL_CAPACITY==16
	//λͰ����,װ���Ԫ��,һ������λ�ÿ�����һ������
	private Node<K, V>[] tables;
	//�������
	private int size;
	//��¼λͰ�����ʣ������
	private int residueCap;

	public Hashtable() {
		//���û��ָ�����ȣ���ʹ��Ĭ�����鳤��
		this(DEFAULT_INITIAL_CAPACITY);
	}
	public Hashtable(int initialCapacity) {
		//���������ռ�
		if(initialCapacity<0){
			throw new RuntimeException("���鳤�Ȳ��Ϸ�");
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
		//[1]ͨ��key��hashֵ����λ����������λ��
		int hash = myHash(key);
		//[2]����key��ȡ���
		Node<K, V> node = getNode(hash,key);
		//�и�key�����ض�Ӧӳ���value
		if(node!=null){
			return node.value;
		}
		//���û�и�key���򷵻�null
		return null;
	}
	
	//����hashֵ��key����Node���
	private Node<K, V> getNode(int hash,K key){
		//[1]ͨ��key��hashֵ����ȡ����Ӧλ������ĵ�һ�����
		Node<K, V> currNode=tables[hash];
		//[2]����equlas()�Ƚ�key
		if(currNode!=null){
			//�Ƿ��Ƕ�Ӧ����������������һ�����
			while(currNode!=null){
				//[3]�и�key�����ض�Ӧ��Node���
				if(currNode.key.equals(key)){
					return currNode;
				}
				//[3]��ָ��������һ��Node
				currNode=currNode.next;
			}
		}
		//[3]û�и�key������null
		return null;
	}

	@Override
	public V put(K key, V value) {
		//[1]ͨ��key��hashֵ����λ����������λ��
		int hash = myHash(key);
		//[2]��Ҫ���ǵ�����
		//��ʱ���ݣ���λͰ����ʣ������==0������key��Ӧ��hashֵ>λͰ���鳤��-1
		if(hash>tables.length-1||residueCap==0){
			//����
			resize();
		}
		System.out.println("hash��Ϊ"+hash+"    λͰ�����С"+tables.length);
		putVal(hash,key,value);
		return value;
	}
	
	//���ݲ��裺
	private void resize(){
		//[1]���¶���һ�����չ������ݺ��λͰ����,�ҳ��ȱ�����2��������,<<1:��ʾ����һ��
		Node<K, V>[] newTables=new Node[tables.length<<1];
		System.out.println("���ݺ�����鳤��Ϊ"+newTables.length);
		//[2]����λͰ�����ֵ�������µ�λͰ������
		Node<K, V> oldNode=null;
		Node<K, V> newNode=null;
		for(int i=0;i<=tables.length-1;i++){
			//[2.1]��ȡ��λͰ�����Ӧ������������
			oldNode= tables[i];
			//[2.2]��������������ĵ�һ����㸳����λͰ����
			newTables[i]=oldNode;
			//[2.3]��Ӧ���������������ڱ�������һ�����
			newNode=newTables[i];
			//[2.4]�жϾ�λͰ�����Ӧ�������������Ƿ�����һ��
			while(oldNode!=null){
				//[2.5]����λͰ�����Ӧ������������ָ��������һ��Node
				oldNode=oldNode.next;
				/*[2.6]����λͰ�����Ӧ���������������һ������nextָ���λͰ�����Ӧ���������������һ�����,
				�Ӷ��γ�����
				*/
				newNode.next=oldNode;
				//����λͰ�����Ӧ������������ָ��������һ��Node
				newNode=oldNode;
			}
		}
		//[3]��ָ��ָ����λͰ����
		tables=newTables;
	}
		
	//��ӽ��
	private void putVal(int hash, K key, V value) {
		/*[2]�жϸ�λ���Ƿ��Ѿ���Node��(���������ϵ�����Ϊ��)������У������equals()�ж��Ƿ����key�ظ��������
		 ����ֱ�Ӽӵ������У����key�����ظ����򸲸�keyӳ��ľ�value,����׷�ӵ������ĩβ
		*/
		Node<K, V> currNode = tables[hash];
		//[3]�½�һ�����
		Node<K, V> newNode=new Node<>(hash,key,value,null);
		//����key�Ƿ��ظ��ı�־λ
		boolean isKeyRepeated=false;
		//���ڱ��������һ�����
		Node<K, V> lastNode=null;
		if(currNode==null){
			//[2.1]����Node�ӵ�������
			tables[hash]=newNode;
			//���������-1
			residueCap--;
			System.out.println("λͰ����ʣ������"+residueCap);
		}else{
			//[2.2]�ж��Ƿ����key�ظ������,��Ҫ��һ��������
			while(currNode!=null){
				//[2.3]�����ͬ���򸲸�keyӳ��ľ�value
				if(currNode.key.equals(key)){
					currNode.value=value;
					isKeyRepeated=true;
					return;
				}
				//[2.4]��¼���ڱ��������һ�����
				lastNode=currNode;
				//[2.5]��ָ��������һ��Node
				currNode=currNode.next;
			}
			//���������key�ظ�����������½ڵ�׷�ӵ�����ĩβ
			if(!isKeyRepeated){
				//[4]������½��֮ǰ���������һ������nextָ���½��
				lastNode.next=newNode;
			}
		}
		//[5]������+1
		size++;
	}
	/**
	 * ��ȡhashֵ
	 * @param key ����
	 * @return
	 */
	public int myHash(K key){
		//����hash����:hashCodeֵ%λͰ���鳤��-1
		return (key.hashCode())%(tables.length-1);
	}
	
	@Override
	public boolean isEmpty() {
		return size==0?true:false;
	}

	@Override
	public boolean containsKey(K key) {
		//[1]�ж��Ƿ���ڸ�key��Ӧ�Ľ��,����У��򷵻�true�����򷵻�false
		Node<K, V> node = getNode(myHash(key), key);
		return node==null?false:true;
	}

	@Override
	public boolean containsValue(V value) {
		//[1]����λͰ����,�Ƿ���ڸ�key��Ӧ�Ľ��,����У��򷵻�true�����򷵻�false
		Node<K, V> currNode =null;
		//������ϣ��
		for(int i=0;i<=tables.length-1;i++){
			//[1]��ȡ��Ӧ������������
			currNode= tables[i];
			//[2]�ж��Ƿ�����һ��
			while(currNode!=null){
				//����equals�ж�value�Ƿ���ͬ
				if(currNode.value.equals(value)){
					return true;
				}
				//[3]��ָ��������һ��Node
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
		//[1]����key��hashֵ��key��ȡ���
		Node<K, V> node = getNode(myHash(key),key);
		//[2]���ڸü����򷵻�keyӳ���value
		if(node!=null){
			//[2]�Ƴ��ý��
			removeNode(node);
			return node.value;
		}
		//[3]�����ڣ�����null
		return null;
		
	}

	/**
	 * ɾ���������--Ҫ�����ͷ�ɾ���ڵ�Ŀռ�,��ֹ�ڴ�й©
	 * 	1.�ǵ�һ����㣬�Ҹ�����������ֻ����һ�����
	 * 		������[1]��λͰ�������������ֵ��Ϊnull
	 * 			 
	 *  2.�ǵ�һ����㣬�Ҹ�������������ڶ�����
	 *  	������[1]����ǰ���ĺ������������ĵ�һ����㣬��tables[i]=�������
	 *   		 [2]����ǰ�����Ϊnull
	 *  
	 *  3.���м�Ľ��
	 *  	������[1]����ǰ����ǰ������nextָ��ǰ���ĺ������
	 *  		 [2]����ǰ�����Ϊnull
	 *  
	 *  4.�����һ�����
	 *  	������[1]����ǰ����ǰ������next��Ϊnull
	 *  		 [2]����ǰ�����Ϊnull
	 */
	private void removeNode(Node<K, V> node) {
		//����������ĵ�һ�����
		Node<K, V> firstNode = tables[node.hash];
		//[2]��ȡ��ǰ���ĺ�̽��,�Ա��ж�ɾ���Ľ��λ��
		Node<K, V> nextNode = node.next;
		//[3]�ж�ɾ���ڵ��λ��
		//[3.1]ɾ�����ǵ�һ�����,�Ҹ�������������ֻ����һ�����,�������һ�����
		if(nextNode==null){
			//[3.1.1]ɾ�����ǵ�һ�����,�Ҹ�������������ֻ����һ�����
			if(firstNode==node){
				//[3.1.1.1]��λͰ�������������ֵ��Ϊnull
				tables[node.hash]=null;
			}
			//[3.1.2]ɾ���������һ�����
			else{
				//[3.1.2.1]����ǰ����ǰ������next��Ϊnull
				getBeforeNode(node).next=nextNode;
				//[3.1.2.2]����ǰ�����Ϊnull
				node=null;
			}
		}
		//[3.2]ɾ���ǵ�һ����㣬�Ҹ�������������ڶ�����,�����м�Ľ��
		else{
			//[3.2.1]ɾ�����ǵ�һ�����,�Ҹ�������������ڶ�����
			if(firstNode==node){
				//[3.2.1.1]����ǰ���ĺ������������ĵ�һ����㣬��tables[i]=�������
				tables[node.hash]=getAfterNode(node);
				//[3.2.1.2]����ǰ�����Ϊnull
				node=null;
			}
			//[3.2.2]ɾ���������һ�����
			else{
				//[3.2.2.1]����ǰ����ǰ������nextָ��ǰ���ĺ������
				getBeforeNode(node).next=getAfterNode(node);
				//[3.2.2.2]����ǰ�����Ϊnull
				node=null;
			}
		}
		//[5]��������1
		size--;
	}
	
	/*  ��ȡ��ǰ������һ�����
	 * ����������������һ����㣬�򷵻�null
	 */
	private Node<K, V> getBeforeNode(Node<K, V> node){
		//����������ǰָ��Ľ��
		Node<K, V> currNode = tables[node.hash];
		//��������
		while(currNode!=null){
			//�жϵ�ǰ����next�Ƿ�ָ��ָ���Ľ��,���ڼ�������һ����㣬���򷵻�null
			if(currNode.next==node){
				return currNode;
			}
			currNode=currNode.next;
		}
		//����������������һ����㣬�򷵻�null
		return null;
		
	}
	
	/*
	 * ��ȡ��ǰ������һ�����
	 * �����������������һ����㣬�򷵻�null
	 */
	private Node<K, V> getAfterNode(Node<K, V> node){
		//����������ǰָ��Ľ��
		Node<K, V> currNode = tables[node.hash];
		//��������
		while(currNode!=null&&node!=null){
			//�жϵ�ǰ����next�Ƿ�ָ��ָ���Ľ��,���ڼ�������һ����㣬���򷵻�null
			if(node.next==currNode){
				return currNode;
			}
			currNode=currNode.next;
		}
		//������������������һ����㣬�򷵻�null
		return null;
	}
	
	@Override
	public V replace(K key, V value) {
		//[1]����key��hashֵ��key�ҵ����
		Node<K, V> node = getNode(myHash(key),key);
		//[2]��¼��ֵ���Ա㷵��
		V oldVal=node.value;
		//[2]����ֵ�滻����ֵ
		node.value=value;
		return oldVal;
	}
	
	//��ӡ��ʽ{key=value,key=value,....}
	@Override
	public String toString() {
		if(size==0){
			return "{}";
		}
		StringBuffer buffer=new StringBuffer("{");
		Node<K, V> currNode =null;
		//������ϣ��
		for(int i=0;i<=tables.length-1;i++){
			//[1]��ȡ��Ӧ������������
			currNode= tables[i];
			//[2]�ж��Ƿ�����һ��
			while(currNode!=null){
				buffer.append(currNode.key+"="+currNode.value+",");
				//[3]��ָ��������һ��Node
				currNode=currNode.next;
			}
		}
		buffer.setCharAt(buffer.length()-1,'}');
		return buffer.toString();
	}
	/**
	 * ���:һ����㣨���ӷ��ͣ������ĸ�����
	 * 		1.hashֵ=key��hashcode��%λͰ���鳤��-1
	 * 		2.key
	 * 		3.value
	 *      3.��һ�����ָ���� nxet
	 * @author HKM
	 *
	 */
	private static class Node<K,V> {
		//key
		K key;
		//value
		V value;
		//hashֵ
		int hash;
		//��һ�����ָ����
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
