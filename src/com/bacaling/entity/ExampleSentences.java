package com.bacaling.entity;

public class ExampleSentences extends Word {
	private int sentenceId;
	private String content;
	private String translation;
	
	public ExampleSentences(String word) {
		super(word);
	}
	
	public int getSentenceId() {
		return sentenceId;
	}
	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
}
