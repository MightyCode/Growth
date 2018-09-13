package growth.sound;

import growth.screen.GameManager;
import growth.screen.screens.GameScreen;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class Sound {

    public static final int NOISE = 0;
    public static final int MUSIC = 1;

    private int sourcePointer, bufferPointer;

    private boolean loop;

    private int type;

    public Sound(String path, int type){
        this(path, type, false);
    }

    public Sound(String path, int type, boolean loop){
        this.type = type;
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);

        ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);

        //Retreive the extra information that was stored in the buffers by the function
        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();
        //Free the space we allocated earlier
        stackPop();
        stackPop();

        //Find the correct OpenAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        //Request space for the buffer
        bufferPointer = alGenBuffers();

        //Send the data to OpenAL
        alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);

        //Free the memory allocated by STB
        free(rawAudioBuffer);

        //Request a source
        sourcePointer = alGenSources();
        alSourcei(sourcePointer, AL_BUFFER, bufferPointer);
        this.loop = loop;
    }

    public void play(){
        float volume = GameManager.soundManager.getVolume(type);
        //Assign the sound we just loaded to the source
        alSourcef(sourcePointer, AL_GAIN, volume);
        alSourcef(sourcePointer, AL_LOOPING, loop?1:0);
            alSourcePlay(sourcePointer);
    }

    public void pause(){
        float volume = GameManager.soundManager.getVolume(type);
        //Assign the sound we just loaded to the source
        alSourcef(sourcePointer, AL_GAIN, volume);
        alSourcePause(sourcePointer);
    }

    public void remove(){
        alSourceStop(sourcePointer);
    }

    public int getType(){
        return type;
    }

    public void setLoop(boolean loop){
        this.loop = loop;
    }

    public void reload(){
        alSourcef(sourcePointer, AL_GAIN, GameManager.soundManager.getVolume(type));
    }

    public void unload(){
        alDeleteSources(sourcePointer);
        alDeleteBuffers(bufferPointer);
    }

}
