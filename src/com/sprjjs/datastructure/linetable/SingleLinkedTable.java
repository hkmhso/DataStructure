package com.sprjjs.datastructure.linetable;

import com.sprjjs.datastructure.interfaces.MyIterator;
import com.sprjjs.datastructure.interfaces.MyList;
import com.sprjjs.datastructure.pojo.MyException;

/**
 * 2.����--��ʽ�洢�ṹ
 * 		��������--���ӷ���
 * @author HKM
 *
 */
public class SingleLinkedTable<E> implements MyList<E>{
	//ͷ��㣬������κε����ݶ������������
	private Node<E> head=new Node<>();
	//���Ԫ��
	private int size;
	
	public SingleLinkedTable() {
		super();
	}
	
	/**
	 * ���:һ����㣨���ӷ��ͣ�������������
	 * 		1.������
	 * 		2.ָ����ָ����һ�����ĵ�ַ��
	 * @author HKM
	 *
	 */
	private static class Node<E> {
		//������
		E data;
		//ָ����
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
		//PS:��Ϊhead��㲻���κε�����Ԫ�أ�������Ҫ��ָ�������һ�����
		Node<E> p=head;
		for(int j=0;j<=i;j++){
			//PS:��ָ��������һ�����
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
		//û���ڸ�Ԫ�أ��򷵻�-1
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//��ȡ������Ӧ�Ľ��
			E data=get(i);
			//�Ƚ����ݣ�������==,����Ƚϵ��ǵ�ַ
			if(data.equals(e)){
				index=i;
				break;
			}
		}
		return index;
	}

	//����У��
	private void rangeCheckForAdd(int ...i){
		if(i!=null&&i.length>0){
			if(i[0]<0||i[0]>size){
				throw new MyException("����Խ��--����"+i[0]);
			}
		}
		
	}
	
	//ɾ�����滻..У��
	private void rangeCheck(int ...i){
		if(i!=null&&i.length>0){
			if(i[0]<0||i[0]>=size){
				throw new MyException("����Խ��--ɾ�����滻"+i[0]);
			}
		}
	}

	@Override
	public int lastIndexOf(E e) {
		//û���ڸ�Ԫ�أ��򷵻�-1
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//��ȡ������Ӧ�Ľ��
			Object data=get(i);
			//�Ƚ����ݣ�������==,����Ƚϵ��ǵ�ַ
			if(data.equals(e)){
				index=i;
			}
		}
		return index;
	}

	//����һ�����,�������£�
	@Override
	public void add(int i, E e) {
		rangeCheckForAdd(i);
		//[1]�½�һ�����
		Node<E> newNode=new Node<>(e);
		//��[2]����ǰ����Ҫ���ҳ�ԭ������Ϊi����ǰһ����㣬��ͷ��㿪ʼ��
		Node<E> p=head;
		for(int j=0;j<i;j++){
			//PS:��ָ��������һ�����
			p=p.next;
		}
		//[2]ָ���½���ֱ�Ӻ�̽��
		newNode.next=p.next;
		//[3]����ԭ������Ϊi����ǰһ�����(Ҳ��������Ϊi-1�Ľ��)��ֱ�Ӻ�̽��
		p.next=newNode;
		//[4]������+1
		size++;
	}

	@Override
	public void add(E e) {
		add(size,e);
	}

	@Override
	public boolean addBefore(E obj, E e) {
		//[1]��ȡobj������
		int i=indexOf(obj);
		if(obj!=null&&i!=-1){
			//[2]��i������e�ڵ�
			add(i,e);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAfter(E obj, E e) {
		//[1]��ȡobj������
		int i=indexOf(obj);
		if(obj!=null&&i!=-1){
			//[2]��i+1������e�ڵ�
			add(i+1,e);
			return true;
		}
		return false;
	}

	@Override
	public E remove(int i) {
		rangeCheck(i);
		//[1].�ҵ�i-1�����Ľ�㣬��ͷ��㿪ʼ��
		Node<E> p=head;
		for(int j=0;j<i;j++){
			//PS:��ָ��������һ�����
			p=p.next;
		}
		//[2]��¼ɾ���Ľ��
		Node<E> removeNode=p.next;
		//[3]����ԭ������Ϊi����ǰһ�����(Ҳ��������Ϊi-1�Ľ��)��ֱ�Ӻ�̽��
		p.next=p.next.next;
		//[4]������-1
		size--;
		return removeNode.data;
	}

	@Override
	public boolean remove(E e) {
		//[1]����Ҫ�жϸ�Ԫ���Ƿ���ڣ����ڷ���true������false
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
		/*��һ�ַ�ʽ���൱��ɾ��i�ڵ㣬����i�����µĽڵ�
		E data=remove(i);
		add(i,e);*/
		//�ڶ��ַ�ʽ
		//[1]�ȵõ���Ӧ�������Ľ��Ԫ��
		Node<E> node = getNode(i);
		//[2]������ɵ�����
		E oldVal = node.data;
		//[3]�滻���µ�����
		node.data=e;
		return oldVal;
		
	}
	
	/**
	 * ��ȡ��Ӧ�������Ľ�����
	 * @param i ������
	 * @return ������
	 */
	private Node<E> getNode(int i){
		//PS:��Ϊhead��㲻���κε�����Ԫ�أ�������Ҫ��ָ�������һ�����
		Node<E> p=head;
		for(int j=0;j<=i;j++){
			//PS:��ָ��������һ�����
			p=p.next;
		}
		return p;
	}
	
	//�������������,����[1,2,3]
	@Override
	public String toString() {
		if(size==0){
			return "[]";
		}
		StringBuilder builder=new StringBuilder("[");
		//PS:��Ϊhead��㲻���κε�����Ԫ�أ�������Ҫ��ָ�������һ�����
		Node<E> p=head.next;
		for(int i=0;i<=size-1;i++){
			if(i==size-1){
				builder.append(p.data);
			}
			else{
				builder.append(p.data+",");
			}
			//PS:��ָ��������һ�����
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
