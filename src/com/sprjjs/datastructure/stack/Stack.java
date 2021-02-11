package com.sprjjs.datastructure.stack;

import java.util.Deque;
import java.util.LinkedList;

/** 
* 借助栈实现进制转换（10----2） 
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
                       //除以2得到余数作为二进制位 
                       int mod = t%2; 
                       //System.out.print(mod); 
                       //str = mod + str; 
                       deque.push(mod); 
                       //除以2得到商作为被除数继续 
                       int result = t/2; 
                       t = result; 
               } 
               System.out.print(n+"--------->"); 
               while(!deque.isEmpty()){ 
                       System.out.print(deque.pop()); 
               }                 
       } 
} 