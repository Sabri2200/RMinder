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

/*
The `AudioAction` class is an implementation of the `Action` interface in a Java application, specifically designed to handle audio playback.

1. Attributes:
   - `file`: A `File` object representing the path to the audio file that this action will play.
   - `requestPublisher`: An instance of `RequestPublisher` used for publishing requests, likely for error handling.

2. Constructor:
   - The constructor `AudioAction(File file)` initializes the `AudioAction` with a specific audio file. It also retrieves an instance of `RequestPublisher` using `RequestPublisher.getInstance()`.

3. Audio Playback Execution:
   - The `execute()` method, which overrides the method from the `Action` interface, is responsible for playing the audio file.
   - It uses the Java Sound API to open and read the audio file, set up an audio stream, and play it through a `SourceDataLine`.
   - The method includes exception handling to catch and report any issues that occur during audio playback.

4. Audio Format Conversion:
   - The `getOutFormat(AudioFormat inFormat)` method converts the input audio format to a standard playable format (PCM_SIGNED).

5. Streaming Audio Data:
   - The `stream(AudioInputStream in, SourceDataLine line)` method reads audio data from the `AudioInputStream` and writes it to the `SourceDataLine` for playback.

6. Error Handling:
   - If an exception occurs during audio playback, the `execute()` method publishes an exception request using `RequestPublisher`, indicating an error in executing the audio action.

7. Action Type and String Representation:
   - The `getType()` method returns `ActionType.MP3PLAYER`, indicating the type of this action.
   - The `toString()` method provides a string representation of the `AudioAction`, including its type and the file path.

8. File Accessor:
   - The `getFile()` method provides access to the `File` object representing the audio file.
*/

public class AudioAction implements Action {
    private final File file;
    private final RequestPublisher requestPublisher;

    public AudioAction(File file) {
        this.file = file;
        this.requestPublisher = RequestPublisher.getInstance();
    }

    @Override
    public void execute() throws AudioActionException {
        try (final AudioInputStream in = getAudioInputStream(file)) {
            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);

            try (final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                }
            }
        } catch (IOException e) {
            // Catch specific IOExceptions, convert them to AudioActionException, and rethrow
            throw new AudioActionException("Error during audio playback");
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            // Catch other specific exceptions and rethrow as AudioActionException
            throw new AudioActionException("Error during audio playback");
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

    @Override
    public String toString() {
        return this.getType().toString() + " " + file.getAbsolutePath();
    }

    public File getFile() {
        return file;
    }
}
