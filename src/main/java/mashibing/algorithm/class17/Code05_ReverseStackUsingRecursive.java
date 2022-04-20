package mashibing.algorithm.class17;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.symmetric.AES;

import java.util.Date;
import java.util.Stack;

public class Code05_ReverseStackUsingRecursive {

	public static void reverse(Stack<Integer> stack) {
		if (stack.isEmpty()) {
			return;
		}
		int i = f(stack);
		reverse(stack);
		stack.push(i);
	}

	// 栈底元素移除掉
	// 上面的元素盖下来
	// 返回移除掉的栈底元素
	public static int f(Stack<Integer> stack) {
		int result = stack.pop();
		if (stack.isEmpty()) {
			return result;
		} else {
			int last = f(stack);
			stack.push(result);
			return last;
		}
	}

	public static void main(String[] args) {
		AES aes = new AES("1gg6acxnea2gjgeo".getBytes());
		String s = aes.decryptStr("1f7ae82a7e2814e852f89663d029be1a095c0a63ea210d3a48d80c00e9652f14ff44b53674b6ef9b4302b4ba137b1712f01f94374387f003cbb8a606329dd78c42cde2a5585861f05efe930866ba7e8014d11fdb1c07c022894361b7186fe145f881afb9f2e6b1eaf9a288a211122ee71a9dc3564a74b403a06f4f04469c266381ff064fcf9581166558369152f9845084324f43a858c7a384b369ebf912c783977f91c73af2c442a6ae92c535d05c45bbabb6b53471ca100c98a4c3a70701f2");
		System.out.println(s);


		Date date = DateTime.now();
		System.out.println(date);
		System.out.println(DateUtil.offsetMinute(date, 60/60));


		Stack<Integer> test = new Stack<Integer>();
		test.push(1);
		test.push(2);
		test.push(3);
		test.push(4);
		test.push(5);
		reverse(test);
		while (!test.isEmpty()) {
			System.out.println(test.pop());
		}

	}

}
