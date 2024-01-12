package SoundManager;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class SoundManager {
	private Synthesizer synthesizer;
	private MidiChannel channel;
	private static final int KEYS_PER_OCTAVE = 12;
	private int octave = 5;
	private int volume = 50;
	
	public SoundManager() {
		startSynthesizer();
	}
	
	private void startSynthesizer() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            channel = synthesizer.getChannels()[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
	
	public void noteOn(int k) {
		channel.noteOn(KEYS_PER_OCTAVE * octave + k, 180);
	}
	
	public void noteOff(int k) {
		channel.noteOff(KEYS_PER_OCTAVE * octave + k);
	}
	
	public void increaseVolume(int x) {
		if (((volume + x) <= 100) && ((volume + x) >= 0))
			volume = volume + x;
		channel.controlChange(7, (int)(volume*1.27));
	}
	
	public void increaseOctave(int x) {
		if ((octave + x <= 8) && (octave + x >= 0))
			octave = octave + x;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public int getOctave() {
		return octave;
	}
}
