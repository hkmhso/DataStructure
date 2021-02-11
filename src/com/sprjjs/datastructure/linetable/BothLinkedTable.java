package com.sprjjs.datastructure.linetable;

import com.sprjjs.datastructure.interfaces.MyIterator;
import com.sprjjs.datastructure.interfaces.MyList;
import com.sprjjs.datastructure.pojo.MyException;

/**
 * 1.˫����--��ʽ�洢�ṹ--ģ��LinkedList
 * 		���ӷ���
 * @author HKM
 *
 */
public class BothLinkedTable<E> implements MyList<E>{
	//�׽��
	private Node<E> first;
	//β���
	private Node<E> last;
	//�������
	private int size;
	
	/**
	 * ���:һ����㣨���ӷ��ͣ�������������
	 * 		1.������
	 * 		2.��һ�����ָ���� prev
	 *      3.��һ�����ָ���� next
	 * @author HKM
	 *
	 */
	private static class Node<E> {
		//������
		E data;
		//��һ�����ָ����
		Node<E> next;
		//��һ�����ָ����
		Node<E> prev;
		
		Node(Node<E> prev,E data, Node<E> next) {
			super();
			this.data = data;
			this.next = next;
			this.prev = prev;
		}

	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public E get(int i) {
		return getNode(i).data;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public boolean contains(E e) {
		return indexOf(e)==-1?false:true;
	}

	@Override
	public int indexOf(E e) {
		//û���򷵻�-1�����򷵻ظýڵ������
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

	@Override
	public int lastIndexOf(E e) {
		//û���򷵻�-1�����򷵻ظýڵ������
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//��ȡ������Ӧ�Ľ��
			E data=get(i);
			//�Ƚ����ݣ�������==,����Ƚϵ��ǵ�ַ
			if(data.equals(e)){
				index=i;
			}
		}
		return index;
	}

	@Override
	public void add(int i, E e) {
		rangeCheckForAdd(i);
		//��������
		if(i==size){
			linkLast(e);
		}
		//�������м����
		else{
			linkBefore(e,getNode(i));
		}
	
	}

	/**
	 * ��e���뵽currNode֮ǰ
	 * @param e
	 * @param currNode
	 */
	private void linkBefore(E e,Node<E> currNode) {
		//[1]��ȡ��ǰ���currNode��ǰһ�����
		Node<E> prevd=currNode.prev;
		//[2]�½�һ����㣬��ָ��nextΪ��ǰ���currNode,prevΪ��ǰ���currNode��ǰһ�����pred
		Node<E> newNode=new Node<>(prevd, e, currNode);
		/*[3]�����ǰ���currNodeΪ��һ����㣬�������ڵ�newNode��Ϊ��һ��Ԫ�أ�
		����ָ����ǰ���currNode��ǰһ�����prevd��nextΪ�����ڵ�newNode,
		��ǰ���currNode��prevΪ�����ڵ�newNode
		*/
		currNode.prev=newNode;
		if(prevd==null){
			first=newNode;
		}else{
			prevd.next=newNode;
			
		}
		//[4]�ڵ����+1
		size++;
	}

	/**
	 * ��ȡ��Ӧ�������Ľ����������۰���ҷ������Ч��
	 * @param i ������
	 * @return ������
	 */
	private Node<E> getNode(int i){
		Node<E> node=null;
		if(i<=(size>>1)){//size>>1:�൱��size/2
			//[1]�ӵ�һ����㿪ʼ��
			node=first;
			for(int j=0;j<i;j++){
				//[2]��ָ��������һ�����
				node=node.next;
			}
		}else{
			//[1]�����һ����ʼ��
			node=last;
			for(int j=size-1;j>i;j--){
				//[2]��ָ��������һ�����
				node=node.prev;
			}
		}
		return node;
	}
	
	//����У��
	private void rangeCheckForAdd(int i){
		if(i<0||i>size){
			throw new MyException("����Խ��--����"+i);
		}
	}
	
	//ɾ�����滻..У��
	private void rangeCheck(int i){
		if(i<0||i>=size){
			throw new MyException("����Խ��--ɾ�����滻"+i);
		}
		
	}
		
	//���뵽���
	@Override
	public void add(E e) {
		linkLast(e);
	}

	/**
	 * ��Ԫ�ز��뵽���
	 * @param e Ԫ��
	 */
	private void linkLast(E e) {
		//ע�⣺�������Ϊ�գ������Ľ�����first��Ҳ��last
		//[1]��ȡ���һ�����lastNode��
		Node<E> lastNode=last;
		//[2]�½����newNode����ָ��nextΪnull(��Ϊ�²���Ľ����ԶΪ���һ�����)��prevΪi-1�Ľ��
		Node<E> newNode=new Node<>(last, e, null);
		//[3]��lastָ�������Ľ��newNode(��Ϊ�²���Ľ����ԶΪ���һ�����)
		last=newNode;
		//[4]�������Ϊ�գ������Ľ���ǵ�һ�����,����ָ��i-1����nextΪ�����Ľ��newNode
		if(lastNode==null){
			first=newNode;
		}else{
			lastNode.next=newNode;
		}
		//�������+1
		size++;
		
	}

	//��e���뵽obj��ǰ��
	@Override
	public boolean addBefore(E obj, E e) {
		//�ж�obj�Ƿ����
		if(contains(obj)){
			add(indexOf(obj),e);
			return true;
		}
		return false;
	}

	//��e���뵽obj�ĺ���
	@Override
	public boolean addAfter(E obj, E e) {
		//�ж�obj�Ƿ����
		if(contains(obj)){
			add(indexOf(obj)+1,e);
			return true;
		}
		return false;
	}

	/**
	 * ɾ���������--Ҫ�����ͷ�ɾ���ڵ�Ŀռ�
	 * 	1.�ǵ�һ�����
	 * 		������[1]����ǰ���i�ĺ������i+1��Ϊfirst
	 * 			 [1]����ǰ���i�ĺ������i+1��prev��Ϊnull
	 * 			 [3]����ǰ���i��next��data��Ϊnull
	 * 			 
	 *  2.�����һ�����
	 *  	������[1]����ǰ���i��ǰ�����i-1��Ϊlast
	 *           [2]����ǰ���i��ǰ�����i-1��next��Ϊnull
	 *  		 [3]����ǰ���i��prev��data��Ϊnull
	 *  3.���м�Ľ��
	 *  	������[1]����ǰ���i��ǰ�����i-1��nextָ��ǰ���i�ĺ������i+1
	 *  		 [2]����ǰ���i�ĺ������i+1��prevָ��ǰ���i��ǰ�����i-1
	 *  		 [3]����ǰ���i��prev,next��data��Ϊnull
	 */
	@Override
	public E remove(int i) {
		rangeCheck(i);
		//[1]���ҵ���ǰʹ��i�ڵ�
		Node<E> currNode = getNode(i);
		E data = currNode.data;
		//[2]��ȡ��ǰ����i��ǰ�����i-1�ͺ�̽��i+1,�Ա��ж�ɾ���Ľ��λ��
		Node<E> prevNode = currNode.prev;
		Node<E> nextNode = currNode.next;
		//[3]�ж�ɾ���ڵ��λ��
		//[3.1]ɾ�����ǵ�һ�����
		if(prevNode==null){
			//[3.1.1]����ǰ���i�ĺ������i+1��Ϊfirst
			first=nextNode;
		}else{
			//PS:ɾ���Ŀ����������м�Ľڵ�
			/*����ǰ���i��ǰ�����i-1��nextָ��ǰ���i�ĺ������i+1�����ɾ����
			�ڵ������һ������nextNodeΪ�գ�����Ϊ��
			*/
			prevNode.next=nextNode;
			//[3.1.2]����ǰ���i��prev��Ϊnull
			currNode.prev=null;
		}
		//[3.2]ɾ���������һ�����
		if(nextNode==null){
			//[3.2.1]����ǰ���i��ǰ�����i-1��Ϊlast
			last=prevNode;
		}else{
			//PS:ɾ���Ŀ����ǵ�һ������м�Ľڵ�
			/*����ǰ���i�ĺ������i+1��prevָ��ǰ���i��ǰ�����i-1�����ɾ����
			�ڵ��ǵ�һ������prevNodeΪ�գ�����Ϊ��
			*/
			nextNode.prev=prevNode;
			//[3.1.2]����ǰ���i��next��Ϊnull
			currNode.next=null;
		}
		//[4]��ǰ���i��data��Ϊnull
		currNode.data=null;
		//[5]��������1
		size--;
		return data;
	}

	@Override
	public boolean remove(E e) {
		//�ж�e�Ƿ����
		if(contains(e)){
			//[1]��ȡe������
			int i = indexOf(e);
			//[2]����ɾ������
			remove(i);
			return true;
		}
		return false;
	}

	@Override
	public E replace(int i, E e) {
		rangeCheck(i);
		//[1]�ȵõ���Ӧ�������Ľ��Ԫ��
		Node<E> node = getNode(i);
		//[2]������ɵ�����
		E oldVal = node.data;
		//[3]�滻���µ�����
		node.data=e;
		return oldVal;
	}
	
	//���磺[1,2,3]
	@Override
	public String toString() {
		if(size==0){
			return "[]";
		}
		StringBuffer builder=new StringBuffer("[");
		//��һ�����
		Node<E> node=first;
		for(int i=0;i<=size-1;i++){
			if(i!=size-1){
				builder.append(node.data+",");
			}else{
				builder.append(node.data);
			}
			//��ָ���ƶ�����һ�����
			node=node.next;
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
