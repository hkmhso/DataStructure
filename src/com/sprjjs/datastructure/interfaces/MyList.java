package com.sprjjs.datastructure.interfaces;

/**
 * ���Ա�ӿ�--���ӷ���
 * �ʹ洢�ṹ�޹�
 * @author Administrator
 *
 */
public interface MyList<E> {
	// �������Ա�Ĵ�С��������Ԫ�صĸ�����
	int size();
	
	// �������Ա������Ϊ i ������Ԫ��
	E get(int i);

	// ������Ա�Ϊ�շ��� true�����򷵻� false��
	boolean isEmpty();

	// �ж����Ա��Ƿ��������Ԫ�� e
	boolean contains(E e);

	// ��������Ԫ�� e��һ�� �����Ա��е����
	int indexOf(E e);

	// ��������Ԫ�� e���һ�� �����Ա��е����
	int lastIndexOf(E e);
	
	// ������Ԫ�� e ���뵽���Ա��� i ��λ��
	void add(int i, E e);
	
	// ������Ԫ�� e ���뵽���Ա�ĩβ
	void add(E e);

	// ������Ԫ�� e ���뵽Ԫ�� obj ֮ǰ
	boolean addBefore(E obj, E e);

	// ������Ԫ�� e ���뵽Ԫ�� obj ֮��
	boolean addAfter(E obj, E e);

	// ɾ�����Ա������Ϊ i ��Ԫ��,������֮
	E remove(int i);

	// ɾ�����Ա��е�һ���� e ��ͬ��Ԫ��
	boolean remove(E e);

	// �滻���Ա������Ϊ i ������Ԫ��Ϊ e������ԭ����Ԫ��
	E replace(int i, E e);
	
	//��ȡ����������
	MyIterator<E> iterator();

	
}
