package ru.caloricity.main;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class CaloricityApplicationTests {

	@Test
	void contextLoads() {
	}

	public boolean isPalindrome(String s) {
		StringBuilder stringBuilder = new StringBuilder();
		for (char c : s.toLowerCase().toCharArray()) {
			if (c >= 'a' && c <= 'z') {
				stringBuilder.append(c);
			}
		}
		String originalStr = stringBuilder.toString();

		return stringBuilder.reverse().toString().equals(originalStr);
	}


	@Test
	void test() {
		HashMap<String, Integer> map = new HashMap<>();
		Set<String> words = Arrays.stream("ADC D DJDOLAD".split(" ")).filter((String str) -> str.length() > 1).collect(Collectors.toSet());
		System.out.println(words);
		for (String word: words) {
			for (int i = 1; i < word.length(); i++) {
				String twoLetter = String.valueOf(word.charAt(i - 1)) + word.charAt(i);
                map.merge(twoLetter, 1, Integer::sum);
			}
		}


		System.out.println(map);
	}



}

class Solution {
	public int maxArea(int[] height) {
		int l = 0, r = height.length - 1;
		int max = new WaterCounter(r,l, height[r], height[l]).calc();

		while (r > l) {
			int minLeft = Math.min(height[l], height[r - 1]);
			int minRight = Math.min(height[r], height[l + 1]);

			if (minLeft < minRight) {
				l++;
			}

			if (minLeft > minRight) {
				r--;
			}

			if (minLeft == minRight) {
				l++;
			}

			max = Math.max(max, new WaterCounter(r,l, height[r], height[l]).calc());
		}

	return max;
	}
}

class WaterCounter {
	int r;
	int l;
	int rHeight;
	int lHeight;

	public WaterCounter(int r, int l, int rHeight, int lHeight) {
		this.r = r;
		this.l = l;
		this.rHeight = rHeight;
		this.lHeight = lHeight;
	}

	public int calc() {
		return (l - r) * Math.max(rHeight, lHeight);
	}
}

