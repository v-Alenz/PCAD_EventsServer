package com.eventi.gui.GUI.Client;

import java.awt.*;
import javax.swing.*;

public class ClientFrame extends JFrame {

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

    public ClientFrame() {

        super("Gestione Eventi Client");
        this.setPreferredSize(new Dimension(900, 420));
        ClientListener myListener = new ClientListener(this);

        
        /////////////////////////////////Pannello output//////////// 
        JPanel outputpanel = new JPanel();
        outputpanel.setLayout(new BoxLayout(outputpanel, BoxLayout.Y_AXIS));
        lista = new JButton("Stampa Eventi Disponibili");
        lista.setActionCommand("stampa");
        lista.addActionListener(myListener);
        leftLabel = new JLabel("Lista Eventi:");
        leftLabel.setVisible(true);
        leftLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        eventBox = new JTextArea();
        eventBox.setColumns(50);
        eventBox.setRows(30);
        eventBox.append("Premere il bottone sottostante per stampare la lista degli eventi...");
        eventBox.setEditable(false);
        scrollableEventBox = new JScrollPane(eventBox);
        scrollableEventBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputpanel.add(leftLabel);
        outputpanel.add(scrollableEventBox);
        outputpanel.add(lista, Panel.CENTER_ALIGNMENT);

        /////////////////////////////////Pannello input form//////////// 
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new BoxLayout(inputpanel, BoxLayout.Y_AXIS));
        postiLabel = new JLabel("Inserisci Posti da prenotare : ");
        eventoLabel = new JLabel("Inserisci Evento da prenotare : ");
        postiLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        postiLabel.setAlignmentY(Panel.CENTER_ALIGNMENT);
        eventoLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        eventoLabel.setAlignmentY(Panel.CENTER_ALIGNMENT);
       
        eventoField = new TextField();
        postiField = new TextField();
        postiField.setPreferredSize(new Dimension(300, 20));
        eventoField.setPreferredSize(new Dimension(300, 20));
        postiField.setMaximumSize(new Dimension(300,20));
        eventoField.setMaximumSize(new Dimension(300,20));

         /////////////////////////////////Pannello input bottoni//////////// 
        prenota = new JButton("Prenota");
        prenota.setActionCommand("prenota");
        prenota.addActionListener(myListener);
        annulla = new JButton("Annulla");
        annulla.setActionCommand("annulla");
        annulla.addActionListener(myListener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        lista.setAlignmentX(Panel.CENTER_ALIGNMENT);
        lista.setAlignmentY(Panel.CENTER_ALIGNMENT);
        annulla.setAlignmentX(Panel.CENTER_ALIGNMENT);
        annulla.setAlignmentY(Panel.CENTER_ALIGNMENT);

        annulla.setEnabled(false);
        buttonPanel.add(prenota);
        buttonPanel.add(annulla);
        inputpanel.add(new JSeparator() );
    
        inputpanel.add(eventoLabel);
        inputpanel.add(eventoField);
        inputpanel.add(postiLabel);
        inputpanel.add(postiField);
        inputpanel.add(buttonPanel);


        JPanel statepanel = new JPanel();
        statepanel.setLayout(new BoxLayout(statepanel, BoxLayout.Y_AXIS));
        stateLabel = new JLabel("Stato : in attesa di prenotazioni");
        stateLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);

        statepanel.add(stateLabel);

        getContentPane().setLayout(new BorderLayout(10, 10));

        getContentPane().add(inputpanel, BorderLayout.WEST);
        getContentPane().add(outputpanel, BorderLayout.CENTER);
       

        getContentPane().add(statepanel, BorderLayout.PAGE_END);

        pack();
        setVisible(true);

    }

    public static void main(String[] args) {

        Runnable init = new Runnable() {
            public void run() {
                new ClientFrame();
            }
        };
        // creo la GUI nell’EDT
        SwingUtilities.invokeLater(init);
    }
}
