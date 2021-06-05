package com.digitalse.cbm.ocr.utils;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class TokenExtract {
    
    public static String[] token(String text){
		Tokenizer tk = WhitespaceTokenizer.INSTANCE;
		return tk.tokenize(text);
    }
}
