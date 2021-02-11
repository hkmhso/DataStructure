package com.sprjjs.datastructure.test;

import java.util.LinkedList;
import java.util.List;

import com.sprjjs.datastructure.interfaces.MyList;
import com.sprjjs.datastructure.linetable.SingleLinkedTable;
/**
 * ���Ե�����
 * @author HKM
 *
 */
public class TestSingleLinkedTable {
	public static void main(String[] args) {
		MyList<String> list=new SingleLinkedTable<>();
		List<String> list1=new LinkedList<String>();
		list.add("123");
		list.add("777");
		list.add(0,"999");
		list.add("123");
		list.add(0,"888");
		System.out.println("ɾ��ǰ������:"+list.toString());
		System.out.println("ɾ��ǰ���鳤��:"+list.size());
		System.out.println("����Ϊ1��Ԫ��:"+list.get(1));
		System.out.println("123��һ�γ��ֵ�λ��:"+list.indexOf("123"));
		System.out.println("123���һ�γ��ֵ�λ��:"+list.lastIndexOf("123"));
		System.out.println("�����Ƿ����466��Ԫ��:"+list.contains("777"));
		System.out.println("�����Ƿ�Ϊ��"+list.isEmpty());
		System.out.println("ɾ����Ԫ�أ�"+list.remove(1));
		System.out.println("ɾ����״̬��"+list.remove("123"));
		System.out.println("ɾ���������:"+list.toString());
		System.out.println("ɾ�������鳤��:"+list.size());
		System.out.println("�滻��Ԫ��Ϊ"+list.replace(1, "000"));
		System.out.println("�滻�������:"+list.toString());
		System.out.println("��Ԫ�ز��뵽��һ��Ԫ�ص�ǰ���״̬��"+list.addBefore("000","111"));
		System.out.println("��Ԫ�ز��뵽��һ��Ԫ�ص�ǰ��������:"+list.toString());
		System.out.println("��Ԫ�ز��뵽��һ��Ԫ�ص�ǰ������鳤��:"+list.size());
		System.out.println("��Ԫ�ز��뵽��һ��Ԫ�صĺ����״̬��"+list.addAfter("111","567"));
		System.out.println("��Ԫ�ز��뵽��һ��Ԫ�صĺ���������:"+list.toString());
		System.out.println("��Ԫ�ز��뵽��һ��Ԫ�صĺ�������鳤��:"+list.size());
	}

}
