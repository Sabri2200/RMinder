package gruppo13.seproject.essential.action.actionType;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.ActionException.AudioActionException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import java.io.File;
import java.io.IOException;

public class AudioAction implements Action {
    private File filePath;
    private State state;

    public AudioAction(File file) {
        this.filePath = file;
        state = State.ACTIVE;
    }

    @Override
    public void execute() throws AudioActionException {
        try (final AudioInputStream in = getAudioInputStream(filePath)) {

            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);

            try (final SourceDataLine line =
                         (SourceDataLine) AudioSystem.getLine(info)) {

                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                }
            }

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new AudioActionException(e.getMessage());
        }
    }
    
    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();

        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line) throws IOException {
        int bytesRead;
        final byte[] buffer = new byte[4096];

        while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
            line.write(buffer, 0, bytesRead);
        }
    }

    @Override
    public ActionType getType() {
        return ActionType.MP3PLAYER;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getFilePath() {
        return String.valueOf(filePath);
    }
}
