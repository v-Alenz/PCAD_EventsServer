package com.eventi.gui.GUI;

import java.awt.*;
import javax.swing.*;


public class Client extends JFrame {

    JButton lista;
    JButton prenota;
    JButton annulla;
    TextField postiField;
    TextField eventoField;
    JLabel leftLabel;
    JTextArea eventBox;
    JScrollPane scrollableEventBox;
    JLabel stateLabel;;
    JLabel postiLabel;
    JLabel eventoLabel;
    
    public Client() {


        super("Gestione Eventi Client");
        this.setPreferredSize(new Dimension(700, 420));
        Listener myListener = new Listener(this);



        JPanel outputpanel = new JPanel();
        outputpanel.setLayout(new BoxLayout(outputpanel, BoxLayout.Y_AXIS));
        lista = new JButton("Stampa Eventi Disponibili");
        lista.setActionCommand("stampa");
        lista.addActionListener(myListener);
        leftLabel = new JLabel("Lista Eventi:");
        leftLabel.setVisible(true);
        leftLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        eventBox = new JTextArea();
        eventBox.setColumns(45);
        eventBox.setRows(20);
        eventBox.setEditable(false);

        scrollableEventBox = new JScrollPane(eventBox);
        scrollableEventBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  

        stateLabel = new JLabel("Stato : In attesa di prenotazioni");
        stateLabel.setAlignmentX(BoxLayout.PAGE_AXIS);
        
        outputpanel.add(leftLabel);
        outputpanel.add(scrollableEventBox);
       
       

        JPanel inputpanel = new JPanel();
        inputpanel.setLayout( new BoxLayout(inputpanel,BoxLayout.Y_AXIS));
       
        prenota = new JButton("Prenota");
        prenota.setActionCommand("prenota");
        prenota.addActionListener(myListener);
        annulla = new JButton("Annulla");
        annulla.setActionCommand("annulla");
        annulla.addActionListener(myListener);
        postiLabel = new JLabel("Inserisci Posti da prenotare : ");
        eventoLabel = new JLabel("Inserisci Evento da prenotare : ");
        postiLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        eventoLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        prenota.setAlignmentX(Panel.CENTER_ALIGNMENT);
        lista.setAlignmentX(Panel.CENTER_ALIGNMENT);
        annulla.setAlignmentX(Panel.CENTER_ALIGNMENT);
        annulla.setAlignmentY(Panel.BOTTOM_ALIGNMENT);
        annulla.setEnabled(false);
        eventoField = new TextField();
        postiField = new TextField();
        
        inputpanel.add(lista);
        inputpanel.add(eventoLabel);
        inputpanel.add(eventoField);
        inputpanel.add(postiLabel);
        inputpanel.add(postiField);
        inputpanel.add(prenota);
        inputpanel.add(annulla);

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(inputpanel);
       
        getContentPane().add(new JSeparator());
        getContentPane().add(new JSeparator());

        getContentPane().add(outputpanel);
        getContentPane().add(stateLabel);

        pack();
        setVisible(true);

    }

    public static void main(String[] args) {

        Runnable init = new Runnable() {
            public void run() {
                new Client();
            }
        };
        // creo la GUI nell’EDT
        SwingUtilities.invokeLater(init);
    }
}
