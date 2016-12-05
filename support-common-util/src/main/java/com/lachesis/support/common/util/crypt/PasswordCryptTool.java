package com.lachesis.support.common.util.crypt;

import java.io.PrintStream;

import com.lachesis.support.common.util.exception.CryptException;

public class PasswordCryptTool {

	public static void main(String[] args) {
		if(args.length < 1){
			printUsage(System.out);
			System.exit(-1);
		}
		
		String plaintext = args[0];
		
		try {
			String digestData = CryptUtils.digest(plaintext);
			printResult(digestData);
		} catch (CryptException e) {
			printResult(e.getMessage(),System.err);
		}
	}
	
	private static void printUsage(PrintStream ps){
		String msg = "Usage:\n";
		msg += "java com.lachesis.support.common.util.crypt <password>";
		
		ps.println(msg);
	}
	
	private  static void printResult(String s){
		printResult(s,null);
	}
	
	private static void printResult(String s, PrintStream ps){
		if(ps == null){
			ps = System.out;
		}
		
		ps.println(s);
	}

}