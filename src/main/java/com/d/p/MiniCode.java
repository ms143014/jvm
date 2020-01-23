package com.d.p;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Keyboard 
 */
public class MiniCode {
	public static class NumPad{
		/**Keyboard letter mapping*/
		public static Map<Character, char[]> numLetters = new HashMap<Character, char[]>();
		static {
			numLetters.put('2', new char[] {'a', 'b', 'c'});
			numLetters.put('3', new char[] {'d', 'e', 'f'});
			numLetters.put('4', new char[] {'g', 'h', 'i'});
			numLetters.put('5', new char[] {'j', 'k', 'l'});
			numLetters.put('6', new char[] {'m', 'n', 'o'});
			numLetters.put('7', new char[] {'p', 'q', 'r', 's'});
			numLetters.put('8', new char[] {'t', 'u', 'v'});
			numLetters.put('9', new char[] {'w', 'x', 'y', 'z'});
		}
		/**input chars*/
		private char[] inputs;
		public NumPad(String input) {
			this.inputs = input.toCharArray(); 
			transfer(0);
		}
		private List<String> outputs = new ArrayList<>();
		/**output combinations of input mapping letters*/
		public List<String> getOutputs() {
			return outputs;
		}
		/**
		 * loop input letters in recursion
		 * */
		private void transfer(int inputIndex) {
			if(inputIndex < inputs.length) {
				char letter = inputs[inputIndex];
				char[]letters = numLetters.get(letter);
				//if found any letters
				if(letters != null) {
					List<String> outputReturn = new ArrayList<String>(); 
					if(outputs.isEmpty()) { //for the first mapped letter
						for(int i = 0; i < letters.length; i++) {
							outputReturn.add(String.valueOf(letters[i]));
						}
					}else {
						//loop for the previous combinations and get the new combinations
						for(String prefix : outputs) {
							for(int i =0; i < letters.length; i++) {
								outputReturn.add(prefix + letters[i]);
							}
						}
					}
					outputs = outputReturn; //hold new combinations
					transfer(inputIndex + 1); //loop for the next input letter
				}else {
					transfer(inputIndex + 1); //loop for the next input letter
				}
			}
		}
	}
	public static void main(String[] args) {
		IntStream.range(0, 99).forEach(i->{
			NumPad numPad = new NumPad(String.valueOf(i));
			System.out.println(numPad.getOutputs());
		});
	}
	
	
}
