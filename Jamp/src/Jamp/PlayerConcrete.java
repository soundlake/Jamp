package jamp;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import javax.sound.sampled.*;

public class PlayerConcrete implements Player{
	protected boolean isPlayingNow = false;
	SourceDataLine soundLine = null;
	Thread playingThread = null;
    int nBytesRead = 0;
    int BUFFER_SIZE = 64*1024;  // 64 KB
    byte[] sampledData = new byte[BUFFER_SIZE];
    boolean puased = false; 
	File soundFile = null;
    

	@Override
	public boolean isPlaying() {
		return isPlayingNow;
	}

	@Override
	public void play() {
	  	// TODO Auto-generated method stub
		isPlayingNow = true;
		playingThread = new Thread(new Runnable() {
			@Override
			public void run() {
		        // Set up an audio input stream piped from the sound file.
			    try {
			        //soundFile濡�遺�꽣 AudioInputStream 媛앹껜 �앹꽦
			        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			        AudioFormat audioFormat = audioInputStream.getFormat();
			        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			        soundLine = (SourceDataLine) AudioSystem.getLine(info);
			        soundLine.open(audioFormat);
			        soundLine.start();
			        //�낅젰���앸궇��源뚯� nByte�⑥쐞濡�諛섎났�곸쑝濡��쎌뼱�ㅼ씤��
			        while (nBytesRead != -1 && !puased) {
			            nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
			            if (nBytesRead >= 0) {
			                // Writes audio data to the mixer via this source data line.
			                soundLine.write(sampledData, 0, nBytesRead);
			            }
			        }
			    } catch (UnsupportedAudioFileException ex) {
			        ex.printStackTrace();
			    } catch (IOException ex) {
			        ex.printStackTrace();
			    } catch (LineUnavailableException ex) {
			        ex.printStackTrace();
			    } finally {
			        soundLine.drain();
			        soundLine.close();
			    }
			}
		});
		playingThread.start();
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		isPlayingNow = false;
		puased = true;
        //soundLine.write(b, offset, nBytesRead); 
        //this is the final invocation of write
        soundLine.drain();
        soundLine.stop();
        soundLine.close();
        soundLine = null;
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
	//	soundLine.write(b, offset, nBytesRead); 
		//this is the final invocation of write
		soundLine.drain();
		soundLine.stop();
		soundLine.close();
		soundLine = null;
	}
	@Override
	public void prev() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void fastForward() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rewind() {
		// TODO Auto-generated method stub
		
	}
	@Override
    public void open() {
        // TODO Auto-generated method stub
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "wav files", "wav"); //description,......확장자
        chooser.setFileFilter(filter); //필터 셋팅
        if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
        soundFile = chooser.getSelectedFile();
        }
    }
    @Override
    public void close() {
        // TODO Auto-generated method stub
        	
    }
	@Override
	public void help() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void report() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void about() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void quit() {
		// TODO Auto-generated method stub
		stop();
		System.exit(0);
	}
}
