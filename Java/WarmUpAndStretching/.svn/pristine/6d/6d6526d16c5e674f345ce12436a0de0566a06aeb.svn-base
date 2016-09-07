package anagram;

import java.util.HashMap;

/**
 * This utility class can test whether two strings are anagrams.
 *
 * @author Claude Anderson.
 */
public class Anagram {

	/**
	 * We say that two strings are anagrams if one can be transformed into the
	 * other by permuting the characters (and ignoring case).
	 * 
	 * For example, "Data Structure" and "Saturated Curt" are anagrams, as are
	 * "Elvis" and "Lives".
	 * 
	 * @param s1
	 *            first string
	 * @param s2
	 *            second string
	 * @return true iff s1 is an anagram of s2
	 */
	public static boolean isAnagram(String s1, String s2) {
		// DONE: implement this method
		// change both of them to UpperCase
		s1 = s1.toUpperCase();
		s2 = s2.toUpperCase();
		// if the length is different they are not Anagram
		if (s1.length() != s2.length())
			return false;
		// Use map to recored the appear times of each char
		HashMap<Character, Integer> map = new HashMap<>();
		for (int k = 0; k < s1.length(); k++) {
			char c = s1.charAt(k);
			int tmp = 0;
			if (map.containsKey(c)) {
				tmp = map.get(c);
			} else {
				tmp = 0;
			}
			map.put(s1.charAt(k), tmp + 1);
		}
		for (int k = 0; k < s2.length(); k++) {
			char c = s2.charAt(k);
			int tmp = 0;
			if (map.containsKey(c)) {
				tmp = map.get(c);
			} else {
				tmp = 0;
			}
			// if the number of Char c appears different times in two string,
			// they are not Anagram
			if (0 == tmp)
				return false;
			map.put(s2.charAt(k), tmp - 1);
		}
		return true;
	}
}
