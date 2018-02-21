package com.example.sowmyaram.tcp_server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Button b;
    TextView tv;
    int TCP_SERVER_PORT = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t = new Thread() {
                    public void run() {

                        runTcpServer();
                    }
                };t.start();

            }
        });
    }

    private void runTcpServer() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(TCP_SERVER_PORT);

            //accept connections
            Socket s = ss.accept();
          BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            //receive a message
               String incomingMsg = in.readLine() + System.getProperty("line.separator");
            Log.i("TcpServer", "received: " + incomingMsg);
            tv.append("received: " + incomingMsg);
            //send a message
            String outgoingMsg = "goodbye from port " + TCP_SERVER_PORT + System.getProperty("line.separator");
            PrintWriter p=new PrintWriter(out);
            p.write(outgoingMsg);
            p.flush();
            Log.i("TcpServer", "sent: " + outgoingMsg);
            tv.append("sent: " + outgoingMsg);
            //SystemClock.sleep(5000);
            s.close();
        } catch (InterruptedIOException e) {
            //if timeout occurs
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}