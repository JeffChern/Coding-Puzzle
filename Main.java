import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Rally Health Coding puzzle
 *
 * Time complexity: O(4^n)
 * Space complexity: O(n)
 * n means length of the longest sequences number
 */
public class Main {
	public static void main(String[] args) {
    CodingPuzzle test = new CodingPuzzle();
	  Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an integer number (N)");
		int target = 0;
		if (scan.hasNextInt()) {
			target = scan.nextInt();
//			System.out.println(target);
		} else {
			System.out.println("Please enter a correct number");
		}
		List<String> nums = new ArrayList<>();
		System.out.println("Please enter your sequences number  (esc to exit)");
		while(scan.hasNext()) {
			String tmp = scan.next();
			if (tmp.equals("esc")) break;
		  nums.add(tmp);
	  }

		for (String s : nums) {
	     test.addOperators(target, s);
		}

		scan.close();
    }
}

class CodingPuzzle {
	private List<String> ans;
	private char[] num;
	private char[] exp;
	private int target;

	/**
	 *
	 * @param target target means integer (N)
	 * @param num n means sequences of digits
	 */
	public void addOperators(int target, String num) {
		this.num = num.toCharArray();
		this.ans = new ArrayList<>();
		this.target = target;
		this.exp = new char[num.length() * 2];

		// call my dfs function
		dfs(0, 0, 0, 0);

//		 print results
		if (ans.size() == 0) {
			System.out.println("impossible");
		} else {
			for (String s : ans) {
				System.out.println(s);
			}
		}

		System.out.println(" ");
	}

	private void dfs(int pos, int len, long prev, long curr) {
		if (pos == num.length) {
			if (curr == target)
				ans.add(new String(exp, 0, len));
			return;
		}

		int s = pos;
		int l = len;
		if (s != 0) ++len;

		long n = 0;
		while (pos < num.length) {
			// 0X
			if (num[s] == '0' && pos - s > 0) break;
			n = n * 10 + (int)(num[pos] - '0');

			// too long
			if (n > Integer.MAX_VALUE) break;
			//copy exp
			exp[len] = num[pos];
			len++;
			pos++;

			if (s == 0) {
				dfs(pos, len, n, n);
				continue;
			}

			exp[l] = '+'; dfs(pos, len, n, curr + n);
			exp[l] = '-'; dfs(pos, len, -n, curr - n);
			exp[l] = '*'; dfs(pos, len, prev * n, curr - prev + prev * n);
		}
	}
}
