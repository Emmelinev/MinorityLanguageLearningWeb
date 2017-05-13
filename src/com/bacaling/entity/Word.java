package com.bacaling.entity;

public class Word {
	private int wordId;
	private String word;
	private String wordClass;
	private String wordPronunciation;
	private String wordTranslation;
	private String ofBar;
	private String exampleSentence;
	
	public Word(String word) {
		this.word = word;
	}
	
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getWordClass() {
		return wordClass;
	}
	public void setWordClass(String wordClass) {
		this.wordClass = wordClass;
	}
	public String getWordPronunciation() {
		return wordPronunciation;
	}
	public void setWordPronunciation(String wordPronunciation) {
		this.wordPronunciation = wordPronunciation;
	}
	public String getWordTranslation() {
		return wordTranslation;
	}

	public void setWordTranslation(String wordTranslation) {
		this.wordTranslation = wordTranslation;
	}

	public String getOfBar() {
		return ofBar;
	}
	public void setOfBar(String ofBar) {
		this.ofBar = ofBar;
	}
	public String getExampleSentence() {
		return exampleSentence;
	}
	public void setExampleSentence(String exampleSentence) {
		this.exampleSentence = exampleSentence;
	}
}
