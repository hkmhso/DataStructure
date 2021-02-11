package com.sprjjs.datastructure.linetable;

import com.sprjjs.datastructure.interfaces.MyIterator;
import com.sprjjs.datastructure.interfaces.MyList;
import com.sprjjs.datastructure.pojo.MyException;

/**
 * 1.双链表--链式存储结构--模拟LinkedList
 * 		增加泛型
 * @author HKM
 *
 */
public class BothLinkedTable<E> implements MyList<E>{
	//首结点
	private Node<E> first;
	//尾结点
	private Node<E> last;
	//结点数量
	private int size;
	
	/**
	 * 结点:一个结点（增加泛型）包括三个部分
	 * 		1.数据域
	 * 		2.上一个结点指针域 prev
	 *      3.下一个结点指针域 next
	 * @author HKM
	 *
	 */
	private static class Node<E> {
		//数据域
		E data;
		//下一个结点指针域
		Node<E> next;
		//上一个结点指针域
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
		//没有则返回-1，否则返回该节点的索引
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

	@Override
	public int lastIndexOf(E e) {
		//没有则返回-1，否则返回该节点的索引
		int index=-1;
		for(int i=0;i<=size-1;i++){
			//获取索引对应的结点
			E data=get(i);
			//比较内容，不能用==,否则比较的是地址
			if(data.equals(e)){
				index=i;
			}
		}
		return index;
	}

	@Override
	public void add(int i, E e) {
		rangeCheckForAdd(i);
		//往最后插入
		if(i==size){
			linkLast(e);
		}
		//否则往中间插入
		else{
			linkBefore(e,getNode(i));
		}
	
	}

	/**
	 * 将e插入到currNode之前
	 * @param e
	 * @param currNode
	 */
	private void linkBefore(E e,Node<E> currNode) {
		//[1]获取当前结点currNode的前一个结点
		Node<E> prevd=currNode.prev;
		//[2]新建一个结点，并指明next为当前结点currNode,prev为当前结点currNode的前一个结点pred
		Node<E> newNode=new Node<>(prevd, e, currNode);
		/*[3]如果当前结点currNode为第一个结点，则将新增节点newNode设为第一个元素，
		否则指定当前结点currNode的前一个结点prevd的next为新增节点newNode,
		当前结点currNode的prev为新增节点newNode
		*/
		currNode.prev=newNode;
		if(prevd==null){
			first=newNode;
		}else{
			prevd.next=newNode;
			
		}
		//[4]节点个数+1
		size++;
	}

	/**
	 * 获取对应索引处的结点对象，运用折半查找法，提高效率
	 * @param i 索引处
	 * @return 结点对象
	 */
	private Node<E> getNode(int i){
		Node<E> node=null;
		if(i<=(size>>1)){//size>>1:相当于size/2
			//[1]从第一个结点开始找
			node=first;
			for(int j=0;j<i;j++){
				//[2]将指针移向下一个结点
				node=node.next;
			}
		}else{
			//[1]从最后一个开始找
			node=last;
			for(int j=size-1;j>i;j--){
				//[2]将指针移向上一个结点
				node=node.prev;
			}
		}
		return node;
	}
	
	//插入校验
	private void rangeCheckForAdd(int i){
		if(i<0||i>size){
			throw new MyException("数组越界--插入"+i);
		}
	}
	
	//删除，替换..校验
	private void rangeCheck(int i){
		if(i<0||i>=size){
			throw new MyException("数组越界--删除或替换"+i);
		}
		
	}
		
	//插入到最后
	@Override
	public void add(E e) {
		linkLast(e);
	}

	/**
	 * 将元素插入到最后
	 * @param e 元素
	 */
	private void linkLast(E e) {
		//注意：如果链表为空，则插入的结点既是first，也是last
		//[1]获取最后一个结点lastNode，
		Node<E> lastNode=last;
		//[2]新建结点newNode，并指明next为null(因为新插入的结点永远为最后一个结点)，prev为i-1的结点
		Node<E> newNode=new Node<>(last, e, null);
		//[3]将last指向新增的结点newNode(因为新插入的结点永远为最后一个结点)
		last=newNode;
		//[4]如果链表为空，则插入的结点是第一个结点,否则指明i-1结点的next为新增的结点newNode
		if(lastNode==null){
			first=newNode;
		}else{
			lastNode.next=newNode;
		}
		//结点数量+1
		size++;
		
	}

	//将e插入到obj的前面
	@Override
	public boolean addBefore(E obj, E e) {
		//判断obj是否存在
		if(contains(obj)){
			add(indexOf(obj),e);
			return true;
		}
		return false;
	}

	//将e插入到obj的后面
	@Override
	public boolean addAfter(E obj, E e) {
		//判断obj是否存在
		if(contains(obj)){
			add(indexOf(obj)+1,e);
			return true;
		}
		return false;
	}

	/**
	 * 删除结点的情况--要考虑释放删除节点的空间
	 * 	1.是第一个结点
	 * 		操作：[1]将当前结点i的后驱结点i+1变为first
	 * 			 [1]将当前结点i的后驱结点i+1的prev置为null
	 * 			 [3]将当前结点i的next和data置为null
	 * 			 
	 *  2.是最后一个结点
	 *  	操作：[1]将当前结点i的前驱结点i-1变为last
	 *           [2]将当前结点i的前驱结点i-1的next置为null
	 *  		 [3]将当前结点i的prev和data置为null
	 *  3.是中间的结点
	 *  	操作：[1]将当前结点i的前驱结点i-1的next指向当前结点i的后驱结点i+1
	 *  		 [2]将当前结点i的后驱结点i+1的prev指向当前结点i的前驱结点i-1
	 *  		 [3]将当前结点i的prev,next和data置为null
	 */
	@Override
	public E remove(int i) {
		rangeCheck(i);
		//[1]先找到当前使用i节点
		Node<E> currNode = getNode(i);
		E data = currNode.data;
		//[2]获取当前结点的i的前驱结点i-1和后继结点i+1,以便判断删除的结点位置
		Node<E> prevNode = currNode.prev;
		Node<E> nextNode = currNode.next;
		//[3]判断删除节点的位置
		//[3.1]删除的是第一个结点
		if(prevNode==null){
			//[3.1.1]将当前结点i的后驱结点i+1变为first
			first=nextNode;
		}else{
			//PS:删除的可能是最后或中间的节点
			/*将当前结点i的前驱结点i-1的next指向当前结点i的后驱结点i+1，如果删除的
			节点是最后一个，则nextNode为空，否则不为空
			*/
			prevNode.next=nextNode;
			//[3.1.2]将当前结点i的prev置为null
			currNode.prev=null;
		}
		//[3.2]删除的是最后一个结点
		if(nextNode==null){
			//[3.2.1]将当前结点i的前驱结点i-1变为last
			last=prevNode;
		}else{
			//PS:删除的可能是第一个后或中间的节点
			/*将当前结点i的后驱结点i+1的prev指向当前结点i的前驱结点i-1，如果删除的
			节点是第一个，则prevNode为空，否则不为空
			*/
			nextNode.prev=prevNode;
			//[3.1.2]将当前结点i的next置为null
			currNode.next=null;
		}
		//[4]当前结点i的data置为null
		currNode.data=null;
		//[5]结点个数—1
		size--;
		return data;
	}

	@Override
	public boolean remove(E e) {
		//判断e是否存在
		if(contains(e)){
			//[1]获取e的索引
			int i = indexOf(e);
			//[2]调用删除方法
			remove(i);
			return true;
		}
		return false;
	}

	@Override
	public E replace(int i, E e) {
		rangeCheck(i);
		//[1]先得到对应索引处的结点元素
		Node<E> node = getNode(i);
		//[2]保存结点旧的数据
		E oldVal = node.data;
		//[3]替换成新的数据
		node.data=e;
		return oldVal;
	}
	
	//例如：[1,2,3]
	@Override
	public String toString() {
		if(size==0){
			return "[]";
		}
		StringBuffer builder=new StringBuffer("[");
		//第一个结点
		Node<E> node=first;
		for(int i=0;i<=size-1;i++){
			if(i!=size-1){
				builder.append(node.data+",");
			}else{
				builder.append(node.data);
			}
			//将指针移动到下一个结点
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
