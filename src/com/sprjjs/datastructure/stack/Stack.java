package com.sprjjs.datastructure.stack;

import java.util.Deque;
import java.util.LinkedList;

/** 
* ����ջʵ�ֽ���ת����10----2�� 
* @author Administrator 
* 
*/ 
public class Stack { 
       public static void main(String[] args) {                 
               int n = 13; 
               int t = n; 
               //String str = ""; 
               Deque<Integer>  deque = new LinkedList<Integer>(); 
               while(t>0){ 
                       //����2�õ�������Ϊ������λ 
                       int mod = t%2; 
                       //System.out.print(mod); 
                       //str = mod + str; 
                       deque.push(mod); 
                       //����2�õ�����Ϊ���������� 
                       int result = t/2; 
                       t = result; 
               } 
               System.out.print(n+"--------->"); 
               while(!deque.isEmpty()){ 
                       System.out.print(deque.pop()); 
               }                 
       } 
} 