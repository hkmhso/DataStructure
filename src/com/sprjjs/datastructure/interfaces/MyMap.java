package com.sprjjs.datastructure.interfaces;
/**
 * ��ϣ��ӿ�--���ӷ���
 * �ʹ洢�ṹ�޹�
 * @author Administrator
 *
 */
public interface MyMap<K,V>{
	// ���ع�ϣ��Ĵ�С��������Ԫ�صĸ�����
	public int size();
	
	// ���ع�ϣ����keyӳ���value
	public V get(K key);

	// ���key-value,����value
	public V put(K key,V value);
		
	// �����ϣ��Ϊ�շ��� true�����򷵻� false��
	public boolean isEmpty();

	// �жϹ�ϣ���Ƿ���� ��key
	public boolean containsKey(K Key);
	
	// �жϹ�ϣ���Ƿ���� ��value
	public boolean containsValue(V value);

	// ɾ����ϣ����keyӳ���value,������֮
	public V remove(K Key);

	// �滻��ϣ����keyӳ���value������ԭvalueԪ��
	public V replace(K key, V value);

}
