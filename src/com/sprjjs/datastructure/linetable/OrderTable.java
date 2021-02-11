package com.sprjjs.datastructure.linetable;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

import com.sprjjs.datastructure.interfaces.MyIterator;
import com.sprjjs.datastructure.interfaces.MyList;
import com.sprjjs.datastructure.pojo.MyException;
/**
 * 1.顺序表--顺序存储结构--模拟ArrayList
 * 		增加泛型
 * @author HKM
 *
 */
public class OrderTable<E> implements MyList<E>{
	//默认数组长度
	static final int DEFAULT_INITIAL_CAPACITY = 4;
	//底层采用数组存储,目前还没有确定的长度
	private Object[] elementData;
	//元素的个数，不是给数组分配几个空间
	private int size;

	public OrderTable() {
		//如果没有指定长度，则使用默认数组长度
		this(DEFAULT_INITIAL_CAPACITY);
	}
	public OrderTable(int initialCapacity) {
		//给数组分配空间
		if(initialCapacity<0){
			throw new RuntimeException("数组长度不合法");
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
	 * 转化为泛型
	 * @param i
	 * @return 泛型
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

	/*抛出： RuntimeException - 数组不能为null或者数组长度不能为0)
	                有break,表示获取该元素第一次出现的位置
			没有break,表示获取的是最后一次出现的位置
	 	PS:存在元素则返回该元素所在的索引，否则返回-1
	 	注意：需要和lastIndexOf()区别开，遍历数组的时候要加break
	 */	
	@Override
	public int indexOf(E e) {
		//没存在该元素，则返回-1
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//比较内容，不能用==,否则比较的是地址
			if(convertToE(i).equals(e)){
				index=i;
				break;
			}
		}
		return index;
			
	}
	
	/*抛出： RuntimeException - 数组不能为null或者数组长度不能为0)
	 	PS:存在元素则返回该元素所在的索引，否则返回-1
		 	有break,表示获取该元素第一次出现的位置
			没有break,表示获取的是最后一次出现的位置
	 	注意：需要和indexOf()区别开，遍历数组的时候不要加break
	*/	
	@Override
	public int lastIndexOf(E e) {
		//没存在该元素，则返回-1
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//比较内容，不能用==,否则比较的是地址
			if(convertToE(i).equals(e)){
				index=i;
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
		if(elementData.length<=0&&null==elementData){
			throw new MyException("数组不能为null或者数组长度不能为0--检索元素");
		}
		
	}
	
	//删除，替换..校验
	private void rangeCheck(int ...i){
		if(i!=null&&i.length>0){
			if(i[0]<0||i[0]>=size){
				throw new MyException("数组越界--删除或替换"+i[0]);
			}
		}
		if(elementData.length==0&&null==elementData){
			throw new MyException("数组不能为null或者数组长度不能为0--检索元素");
		}
		
	}

	/*抛出： IndexOutOfBoundsException - 如果索引超出范围 (index < 0 || index > size())
	PS:[1]需要考虑扩容的问题
	   [2]需要从临界值size,首位置0和范围[0,size]三个情况进行分析。
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
	 * 将元素e插入到i位置
	 * @param i 插入位置
	 * @param e 插入元素
	 */
	public void insertBefore(int i,E e) {
		rangeCheckForAdd(i);
		//数组的大小等于元素个数时，进行扩容
		if(size==elementData.length){//数组满了
			grow();
		}
		//PS:只有在i>=0||i<size的范围内插入才需要往后移动操作。
		//将i以及i之后的元素各自往后移动一位
		for(int j=size;j>i;j--){
			//将索引为size的前一位的元素依次往后移动一位，直到索引为i就停止,相当于覆盖掉后一位元素
			elementData[j]=elementData[j-1];
		}
		//给特定位置赋值
		elementData[i]=e;
		size++;
	}
	//扩容步骤：
	private void grow(){
		//[1]重新定义一个按照规律扩容后的数组
		Object[] newElementData=new Object[elementData.length*2];
		//[2]将旧数组的值拷贝到中心数组中
		for(int i=0;i<=size-1;i++){
			newElementData[i]=elementData[i];
		}
		//[3]将指针指向新数组
		elementData=newElementData;
	}

	/*PS:将一个元素插入到另一个元素的前面范围[0,size-1]
	        将元素e插入到元素obj前面,插入成功返回true，否则返回false
	 	元素obj必须存在，元素e不存在，是个新添加的元素
	*/
	@Override
	public boolean addBefore(E obj, E e) {
		if(contains(obj)){//obj元素存在
			//[1]获取obj存在的位置
			int i=indexOf(obj);
			//[2]在i处插入e节点
			insertBefore(i,e);
			return true;
		}
		return false;
	}

	/*PS:将一个元素插入到另一个元素的后面范围[0,size-1]
	       将元素e插入到元素obj后面,插入成功返回true，否则返回false
		元素obj必须存在，元素e不存在，是个新添加的元素
	*/
	@Override
	public boolean addAfter(E obj, E e) {
		if(contains(obj)){//obj元素存在
			//[1]获取obj存在的位置
			int i=indexOf(obj);
			//[2]在i+1处插入e节点
			insertAfter(i+1,e);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 将元素插入到i+1的位置
	 * @param i 插入的位置
	 * @param e 插入的元素
	 */
	private void insertAfter(int i, E e) {
		rangeCheckForAdd(i);
		//数组的大小等于元素个数时，进行扩容
		if(size==elementData.length){//数组满了
			grow();
		}
		//PS:将一个元素插入到另一个元素的后面范围[1,size]
		//将i之后的元素各自往后移动一位
		for(int j=size;j>i;j--){
			//将索引为size的前一位的元素依次往后移动一位，直到索引为i+1就停止,相当于覆盖掉后一位元素
			elementData[j]=elementData[j-1];
		}
		//给特定位置赋值
		elementData[i]=e;
		size++;
	}
	
	/*抛出： IndexOutOfBoundsException - 如果索引超出范围 (index < 0 || index >= size())
	PS:[1]不需要考虑扩容的问题
		 [2]需要从临界值size-1,首位置0和范围[0,size-1]三个情况进行分析。
	*/	
	@Override
	public E remove(int i) {
		//记录被删除的元素节点
		E removeElement=convertToE(i);
		removeElement(i);
		return removeElement;
	}

	/*   存在元素则返回true,且移除，否则返回false
		  PS:[1]不需要考虑扩容的问题
		 	 [2]注意：需要先找出该元素第一次存在的位置
		 	 [3]需要从临界值size-1,首位置0和范围[0,size-1]三个情况进行分析。
	*/	
	@Override
	public boolean remove(E e) {
		//元素存在则返回true，并进行移除,否则返回false
		if(e!=null&&indexOf(e)!=-1){
			//先查出该元素存在的位置，然后再进行移动操作
			//[1]获取该元素存在的位置
			int i=indexOf(e);
			removeElement(i);
			return true;
		}
		return false;
	}

	/**
	 * 删除i位置的元素
	 * @param i 删除的位置
	 */
	private void removeElement(int i){
		rangeCheck(i);
		//将i之后的元素进行往前移动一位
		for(int j=i+1;j<size;j++){
			//将i之后的元素依次往前移动一位，直到索引为size就停止,相当于覆盖掉前一位元素
			elementData[j-1]=elementData[j];
		}
		//元素个数-1
		size--;
	}
	
	@Override
	public E replace(int i, E e) {
		rangeCheck(i);
		//先查出索引为i的元素，再进行替换它
		//[1]查出索引为i的元素，并保存下来，以便返回
		E element=convertToE(i);
		//[2]替换它
		elementData[i]=e;
		return element;
	}
	//获取迭代器对象
	public MyIterator<E> iterator(){
		return new Itr();
	}
	/**
	 * 内部类，具体迭代器类
	 * @author HKM
	 *
	 */
	private class Itr implements MyIterator<E>{
		//游标
		private int cursor;
		@Override
		public boolean hasNext() {
			return cursor!=size;
		}

		@Override
		public E next() {
			//[1]先获取当前游标所指向的元素
			int currCursor=cursor;
			if (currCursor >= size){
	           throw new NoSuchElementException("数组越界，没有该元素");
			}
			//[2]再将游标移向下一个元素
			cursor+=1;
			return convertToE(currCursor);
		}
		
	}
	
	//返回数组的内容,例：[1,2,3]
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
