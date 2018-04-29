package com.example.roland.rtpsteeamingtesttvapp;

import android.media.AudioManager;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    AudioStream audioStream;
    AudioGroup audioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

    }


    @Override
    public void onStart() {

        super.onStart();

        AudioManager audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        assert audio != null;
        audio.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioGroup = new AudioGroup();
        audioGroup.setMode(AudioGroup.MODE_ECHO_SUPPRESSION);
        InetAddress inetAddress;

        try {
            inetAddress = InetAddress.getByName("192.168.1.15");
            audioStream = new AudioStream(inetAddress);
            audioStream.setCodec(AudioCodec.PCMU);
            audioStream.setMode(RtpStream.MODE_NORMAL);
            InetAddress inetAddressRemote = InetAddress.getByName("192.168.1.14");
            audioStream.associate(inetAddressRemote, 1234);
            audioStream.join(audioGroup);
        }
        catch ( UnknownHostException | SocketException e ) {
            e.printStackTrace();
            e.getMessage();
        }

    }

}
