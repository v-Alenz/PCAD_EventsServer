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
        this.setPreferredSize(new Dimension(800, 350));
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

        
        
        outputpanel.add(leftLabel);
        outputpanel.add(scrollableEventBox);
        outputpanel.add(lista,Panel.RIGHT_ALIGNMENT);
       

        JPanel inputpanel = new JPanel();
        inputpanel.setLayout( new BoxLayout(inputpanel,BoxLayout.PAGE_AXIS));
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
        postiField.setPreferredSize(new Dimension(170, 150));
        postiField.setMaximumSize(new Dimension(10000, 200));
        eventoField.setPreferredSize(new Dimension(170, 150));
        eventoField.setMaximumSize(new Dimension(10000, 200));


       
        inputpanel.add(eventoLabel);
        inputpanel.add(eventoField);
        inputpanel.add(postiLabel);
        inputpanel.add(postiField);
        inputpanel.add(prenota);
        inputpanel.add(annulla);


        JPanel statepanel = new JPanel();
        statepanel.setLayout(new BoxLayout(statepanel, BoxLayout.Y_AXIS));
        stateLabel= new JLabel("Stato : in attesa di prenotazioni");
        stateLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        
       
       
        statepanel.add(stateLabel);

        getContentPane().setLayout( new BorderLayout(10,10));
       
        getContentPane().add(inputpanel,BorderLayout.WEST);
    
        getContentPane().add(outputpanel,BorderLayout.CENTER);
       
        getContentPane().add(statepanel,BorderLayout.SOUTH);   
      


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
