package com.sprjjs.datastructure.linetable;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

import com.sprjjs.datastructure.interfaces.MyIterator;
import com.sprjjs.datastructure.interfaces.MyList;
import com.sprjjs.datastructure.pojo.MyException;
/**
 * 1.˳���--˳��洢�ṹ--ģ��ArrayList
 * 		���ӷ���
 * @author HKM
 *
 */
public class OrderTable<E> implements MyList<E>{
	//Ĭ�����鳤��
	static final int DEFAULT_INITIAL_CAPACITY = 4;
	//�ײ��������洢,Ŀǰ��û��ȷ���ĳ���
	private Object[] elementData;
	//Ԫ�صĸ��������Ǹ�������伸���ռ�
	private int size;

	public OrderTable() {
		//���û��ָ�����ȣ���ʹ��Ĭ�����鳤��
		this(DEFAULT_INITIAL_CAPACITY);
	}
	public OrderTable(int initialCapacity) {
		//���������ռ�
		if(initialCapacity<0){
			throw new RuntimeException("���鳤�Ȳ��Ϸ�");
		}else{
			elementData=new Object[initialCapacity];
		}
		
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public E get(int i) {
		return convertToE(i);
	}
	
	/**
	 * ת��Ϊ����
	 * @param i
	 * @return ����
	 */
	@SuppressWarnings("unchecked")
	private E convertToE(int i){
		return (E)elementData[i];
		
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

	/*�׳��� RuntimeException - ���鲻��Ϊnull�������鳤�Ȳ���Ϊ0)
	                ��break,��ʾ��ȡ��Ԫ�ص�һ�γ��ֵ�λ��
			û��break,��ʾ��ȡ�������һ�γ��ֵ�λ��
	 	PS:����Ԫ���򷵻ظ�Ԫ�����ڵ����������򷵻�-1
	 	ע�⣺��Ҫ��lastIndexOf()���𿪣����������ʱ��Ҫ��break
	 */	
	@Override
	public int indexOf(E e) {
		//û���ڸ�Ԫ�أ��򷵻�-1
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//�Ƚ����ݣ�������==,����Ƚϵ��ǵ�ַ
			if(convertToE(i).equals(e)){
				index=i;
				break;
			}
		}
		return index;
			
	}
	
	/*�׳��� RuntimeException - ���鲻��Ϊnull�������鳤�Ȳ���Ϊ0)
	 	PS:����Ԫ���򷵻ظ�Ԫ�����ڵ����������򷵻�-1
		 	��break,��ʾ��ȡ��Ԫ�ص�һ�γ��ֵ�λ��
			û��break,��ʾ��ȡ�������һ�γ��ֵ�λ��
	 	ע�⣺��Ҫ��indexOf()���𿪣����������ʱ��Ҫ��break
	*/	
	@Override
	public int lastIndexOf(E e) {
		//û���ڸ�Ԫ�أ��򷵻�-1
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//�Ƚ����ݣ�������==,����Ƚϵ��ǵ�ַ
			if(convertToE(i).equals(e)){
				index=i;
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
		if(elementData.length<=0&&null==elementData){
			throw new MyException("���鲻��Ϊnull�������鳤�Ȳ���Ϊ0--����Ԫ��");
		}
		
	}
	
	//ɾ�����滻..У��
	private void rangeCheck(int ...i){
		if(i!=null&&i.length>0){
			if(i[0]<0||i[0]>=size){
				throw new MyException("����Խ��--ɾ�����滻"+i[0]);
			}
		}
		if(elementData.length==0&&null==elementData){
			throw new MyException("���鲻��Ϊnull�������鳤�Ȳ���Ϊ0--����Ԫ��");
		}
		
	}

	/*�׳��� IndexOutOfBoundsException - �������������Χ (index < 0 || index > size())
	PS:[1]��Ҫ�������ݵ�����
	   [2]��Ҫ���ٽ�ֵsize,��λ��0�ͷ�Χ[0,size]����������з�����
	*/	
	@Override
	public void add(int i, E e) {
		insertBefore(i,e);
	}

	@Override
	public void add(E e) {
		add(size,e);	
	}
	
	/**
	 * ��Ԫ��e���뵽iλ��
	 * @param i ����λ��
	 * @param e ����Ԫ��
	 */
	public void insertBefore(int i,E e) {
		rangeCheckForAdd(i);
		//����Ĵ�С����Ԫ�ظ���ʱ����������
		if(size==elementData.length){//��������
			grow();
		}
		//PS:ֻ����i>=0||i<size�ķ�Χ�ڲ������Ҫ�����ƶ�������
		//��i�Լ�i֮���Ԫ�ظ��������ƶ�һλ
		for(int j=size;j>i;j--){
			//������Ϊsize��ǰһλ��Ԫ�����������ƶ�һλ��ֱ������Ϊi��ֹͣ,�൱�ڸ��ǵ���һλԪ��
			elementData[j]=elementData[j-1];
		}
		//���ض�λ�ø�ֵ
		elementData[i]=e;
		size++;
	}
	//���ݲ��裺
	private void grow(){
		//[1]���¶���һ�����չ������ݺ������
		Object[] newElementData=new Object[elementData.length*2];
		//[2]���������ֵ����������������
		for(int i=0;i<=size-1;i++){
			newElementData[i]=elementData[i];
		}
		//[3]��ָ��ָ��������
		elementData=newElementData;
	}

	/*PS:��һ��Ԫ�ز��뵽��һ��Ԫ�ص�ǰ�淶Χ[0,size-1]
	        ��Ԫ��e���뵽Ԫ��objǰ��,����ɹ�����true�����򷵻�false
	 	Ԫ��obj������ڣ�Ԫ��e�����ڣ��Ǹ�����ӵ�Ԫ��
	*/
	@Override
	public boolean addBefore(E obj, E e) {
		if(contains(obj)){//objԪ�ش���
			//[1]��ȡobj���ڵ�λ��
			int i=indexOf(obj);
			//[2]��i������e�ڵ�
			insertBefore(i,e);
			return true;
		}
		return false;
	}

	/*PS:��һ��Ԫ�ز��뵽��һ��Ԫ�صĺ��淶Χ[0,size-1]
	       ��Ԫ��e���뵽Ԫ��obj����,����ɹ�����true�����򷵻�false
		Ԫ��obj������ڣ�Ԫ��e�����ڣ��Ǹ�����ӵ�Ԫ��
	*/
	@Override
	public boolean addAfter(E obj, E e) {
		if(contains(obj)){//objԪ�ش���
			//[1]��ȡobj���ڵ�λ��
			int i=indexOf(obj);
			//[2]��i+1������e�ڵ�
			insertAfter(i+1,e);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * ��Ԫ�ز��뵽i+1��λ��
	 * @param i �����λ��
	 * @param e �����Ԫ��
	 */
	private void insertAfter(int i, E e) {
		rangeCheckForAdd(i);
		//����Ĵ�С����Ԫ�ظ���ʱ����������
		if(size==elementData.length){//��������
			grow();
		}
		//PS:��һ��Ԫ�ز��뵽��һ��Ԫ�صĺ��淶Χ[1,size]
		//��i֮���Ԫ�ظ��������ƶ�һλ
		for(int j=size;j>i;j--){
			//������Ϊsize��ǰһλ��Ԫ�����������ƶ�һλ��ֱ������Ϊi+1��ֹͣ,�൱�ڸ��ǵ���һλԪ��
			elementData[j]=elementData[j-1];
		}
		//���ض�λ�ø�ֵ
		elementData[i]=e;
		size++;
	}
	
	/*�׳��� IndexOutOfBoundsException - �������������Χ (index < 0 || index >= size())
	PS:[1]����Ҫ�������ݵ�����
		 [2]��Ҫ���ٽ�ֵsize-1,��λ��0�ͷ�Χ[0,size-1]����������з�����
	*/	
	@Override
	public E remove(int i) {
		//��¼��ɾ����Ԫ�ؽڵ�
		E removeElement=convertToE(i);
		removeElement(i);
		return removeElement;
	}

	/*   ����Ԫ���򷵻�true,���Ƴ������򷵻�false
		  PS:[1]����Ҫ�������ݵ�����
		 	 [2]ע�⣺��Ҫ���ҳ���Ԫ�ص�һ�δ��ڵ�λ��
		 	 [3]��Ҫ���ٽ�ֵsize-1,��λ��0�ͷ�Χ[0,size-1]����������з�����
	*/	
	@Override
	public boolean remove(E e) {
		//Ԫ�ش����򷵻�true���������Ƴ�,���򷵻�false
		if(e!=null&&indexOf(e)!=-1){
			//�Ȳ����Ԫ�ش��ڵ�λ�ã�Ȼ���ٽ����ƶ�����
			//[1]��ȡ��Ԫ�ش��ڵ�λ��
			int i=indexOf(e);
			removeElement(i);
			return true;
		}
		return false;
	}

	/**
	 * ɾ��iλ�õ�Ԫ��
	 * @param i ɾ����λ��
	 */
	private void removeElement(int i){
		rangeCheck(i);
		//��i֮���Ԫ�ؽ�����ǰ�ƶ�һλ
		for(int j=i+1;j<size;j++){
			//��i֮���Ԫ��������ǰ�ƶ�һλ��ֱ������Ϊsize��ֹͣ,�൱�ڸ��ǵ�ǰһλԪ��
			elementData[j-1]=elementData[j];
		}
		//Ԫ�ظ���-1
		size--;
	}
	
	@Override
	public E replace(int i, E e) {
		rangeCheck(i);
		//�Ȳ������Ϊi��Ԫ�أ��ٽ����滻��
		//[1]�������Ϊi��Ԫ�أ��������������Ա㷵��
		E element=convertToE(i);
		//[2]�滻��
		elementData[i]=e;
		return element;
	}
	//��ȡ����������
	public MyIterator<E> iterator(){
		return new Itr();
	}
	/**
	 * �ڲ��࣬�����������
	 * @author HKM
	 *
	 */
	private class Itr implements MyIterator<E>{
		//�α�
		private int cursor;
		@Override
		public boolean hasNext() {
			return cursor!=size;
		}

		@Override
		public E next() {
			//[1]�Ȼ�ȡ��ǰ�α���ָ���Ԫ��
			int currCursor=cursor;
			if (currCursor >= size){
	           throw new NoSuchElementException("����Խ�磬û�и�Ԫ��");
			}
			//[2]�ٽ��α�������һ��Ԫ��
			cursor+=1;
			return convertToE(currCursor);
		}
		
	}
	
	//�������������,����[1,2,3]
	@Override
	public String toString() {
		if(size==0){
			return "[]";
		}
		StringBuilder builder=new StringBuilder("[");
		for(int i=0;i<=size-1;i++){
			if(i==size-1){
				builder.append(elementData[i]);
			}
			else{
				builder.append(elementData[i]+",");
			}
			
		}
		builder.append("]");
		return builder.toString();
	}
	

}
