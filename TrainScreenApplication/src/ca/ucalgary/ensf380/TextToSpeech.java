package ca.ucalgary.ensf380;

import com.sun.speech.freetts.*;

public class TextToSpeech {
    private static final String VOICE_NAME = "kevin16";
    static Voice voice;
    private static TextToSpeech instance;

    public TextToSpeech() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICE_NAME);

        if (voice != null) {
            voice.allocate();
        } else {
            throw new IllegalStateException("Cannot find voice: " + VOICE_NAME);
        }
    }

    public static synchronized TextToSpeech getInstance() {
        if (instance == null) {
            instance = new TextToSpeech();
        }
        return instance;
    }

    public synchronized void speak(String text) {
        if (voice != null) {
            voice.speak(text);
        } else {
            throw new IllegalStateException("Cannot find voice: " + VOICE_NAME);
        }
    }

    public synchronized void deallocate() {
        if (voice != null) {
            voice.deallocate();
            voice = null; // Set to null to prevent further use
        } else {
            throw new IllegalStateException("Cannot find voice: " + VOICE_NAME);
        }
    }

    public static void main(String[] args) {
        TextToSpeech tts = TextToSpeech.getInstance();
        tts.speak("Hello, this is a test of the text-to-speech system.");
        tts.deallocate();
    }
}
