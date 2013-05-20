package study;

import java.io.*;
import javax.sound.sampled.*;

public class StudyStream {
	AudioInputStream in, din;
	AudioFormat base, decoded;
	SourceDataLine line;
	private boolean loop;
	private BufferedInputStream stream;

	// Constructor
	StudyStream(String filename, boolean loop){
		this(filename);
		this.loop = loop;
	}
	StudyStream(String filename){
		this.loop = false;
		try{
			InputStream raw = Object.class.getResourceAsStream(filename);
			stream = new BufferedInputStream(raw);
			in = AudioSystem.getAudioInputStream(stream);
			din = null;
			
			if(in != null){
				base = in.getFormat();
				decoded = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						base.getSampleRate(), 16,
						base.getChannels(),
						base.getChannels() * 2,
						base.getSampleRate(), false);
				din = AudioSystem.getAudioInputStream(decoded, in);
				line = getLine(decoded);
			}
		} catch(UnsupportedAudioFileException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} catch(LineUnavailableException e){
			e.printStackTrace();
		}
	}
	
	// public Methods
	public void reset(){
		try{
			stream.reset();
			in = AudioSystem.getAudioInputStream(stream);
			din = AudioSystem.getAudioInputStream(decoded, in);
			line = getLine(decoded);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public void close(){
		try{
			line.close();
			din.close();
			in.close();
		} catch(IOException e){}
	}
	public void play(){
		try{
			boolean firstTime = true;
			while(firstTime || loop){
				firstTime = false;
				byte[] data = new byte[4096];
				
				if(line != null){
					line.start();
					int nBytesRead = 0;
					
					while(nBytesRead != -1){
						nBytesRead = din.read(data, 0, data.length);
						if(nBytesRead != -1)
							line.write(data, 0, nBytesRead);
					}
					line.drain();
					line.stop();
					line.close();
					
					reset();
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	// private Methods
	private SourceDataLine getLine(AudioFormat audioFormat)
				throws LineUnavailableException {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		SourceDataLine res = (SourceDataLine)AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}

	// main Methods
	public static void main(String[] args) {
		StudyStream sound = new StudyStream("testFiles/test.wav", true);
		sound.play();
	}
}
