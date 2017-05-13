package com.bacaling.util;

import java.io.FileOutputStream;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

public class TTSUtil {
    public String tts (String ttsString) throws Exception {
        VoiceProvider tts = new VoiceProvider("a5d6cea34d5c4aac84b4790307adeebe");
		System.out.println(ttsString);
        VoiceParameters params = new VoiceParameters(ttsString, Languages.Spanish_Spain);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);
		
        String voice = tts.speech(params);
		String str = String.format("<audio src='%s' autoplay='autoplay'></audio>", voice);
        System.out.print(String.format("<audio class=\"word-audio\" src='%s' autoplay='autoplay'></audio>", voice));
        return str;
    }
//    public static String ttsSentence(String ttsString,String speed){
////    	speed-慢速-5 普通速度0
//		 String httpUrl = "http://api.voicerss.org/";
//		 String httpArg = "key=a5d6cea34d5c4aac84b4790307adeebe&hl=es-es&src=" + ttsString + "&r=" + speed;
//		 String jsonResult = HttpRequestUtil.request(httpUrl, httpArg);
//		 System.out.println(jsonResult);
//		 return jsonResult;
//	 }	
}