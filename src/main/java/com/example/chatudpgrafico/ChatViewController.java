package com.example.chatudpgrafico;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.DatagramPacket;


public class ChatViewController {
    private String name;
    private static MulticastSocket socket;
    private volatile boolean alreadyLeaved;
    private InetAddress grupo;
    private int puerto = 8080;
    private HelloApplication application;

    @FXML
    private TextField txtChat;
    @FXML
    private TextArea txaChat;
    @FXML
    private Label lblName;

    public void setApplication(HelloApplication helloApplication){
        application = helloApplication;
    }
    public boolean getAlreadyLeaved(){
        return alreadyLeaved;
    }

    public MulticastSocket getSocket(){
        return socket;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void initialize(){
        try{
            lblName.setText(name);

            grupo = InetAddress.getByName("224.0.0.0");
            socket = new MulticastSocket(puerto);
            alreadyLeaved = false;

            socket.joinGroup(grupo);
            Thread hiloDeLectura = new Thread(new ChatUDP(this));
            hiloDeLectura.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void recieveMessage (String message){
        txaChat.appendText("\n" + message);
    }

    @FXML
    protected void textboxPressEnter(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER && !txtChat.getText().isBlank()){
            sendMessage();
        }
    }

    @FXML
    protected void btnSendAction (){
        if(!txtChat.getText().isBlank()){
            sendMessage();
        }
    }

    private void sendMessage(){

        try{
            String msj = txtChat.getText();

            if(msj.equalsIgnoreCase("Adios")){
                String mensajeFinal = name + ": Ha cerrado la conexión";
                byte[] mensaje = mensajeFinal.getBytes();
                DatagramPacket mensajeSalida = new DatagramPacket(mensaje, mensaje.length, grupo, puerto);
                socket.send(mensajeSalida);

                socket.leaveGroup(grupo);
                alreadyLeaved = true;

                application.close();
            } else{

                String mensajeFinal = name + ": " + msj;
                byte[] mensaje = mensajeFinal.getBytes();
                DatagramPacket mensajeSalida = new DatagramPacket(mensaje, mensaje.length, grupo, puerto);
                socket.send(mensajeSalida);

                txaChat.appendText("\n" + mensajeFinal);
                txtChat.setText("");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
