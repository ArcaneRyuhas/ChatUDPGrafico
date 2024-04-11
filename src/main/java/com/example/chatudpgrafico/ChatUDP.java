package com.example.chatudpgrafico;

import java.net.DatagramPacket;

public class ChatUDP implements Runnable{

    private ChatViewController chatViewController;

    public ChatUDP(ChatViewController chatViewController){
        this.chatViewController = chatViewController;
    }

    @Override
    public void run () {
        byte[] buffer = new byte [1024];
        String linea;

        try {
            while (!chatViewController.getAlreadyLeaved()) {
                DatagramPacket mensajeEntrada = new DatagramPacket(buffer, buffer.length);
                chatViewController.getSocket().receive(mensajeEntrada);

                linea = new String(mensajeEntrada.getData(), 0, mensajeEntrada.getLength());

                if(!linea.startsWith(chatViewController.getName())){
                    chatViewController.recieveMessage(linea);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
