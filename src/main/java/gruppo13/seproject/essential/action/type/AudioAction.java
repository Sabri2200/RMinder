package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.exception.AudioActionException;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;
import java.io.File;
import java.io.IOException;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class AudioAction implements Action {
    private File filePath;
    private RequestPublisher requestPublisher;

    public AudioAction(File file) {
        this.filePath = file;
        this.requestPublisher = RequestPublisher.getInstance();
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

        } catch (Exception e) {
            requestPublisher.publishRequest(RequestFactory.createExceptionRequest(e));
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

    public String getFilePath() {
        return String.valueOf(filePath);
    }

    @Override
    public String toString() {
        return this.getType().toString() + " " + filePath.getAbsolutePath();
    }

    public File getFile() {
        return filePath;
    }
}
