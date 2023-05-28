package com.eventi.gui.GUI.Admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class AdminFrame extends JFrame {

    public JTabbedPane tabFunc;

    public AdminFrame() {

        super("Gestione Eventi Admin");
        this.setPreferredSize(new Dimension(600, 420));
        AdminListener myListener = new AdminListener(this);

        tabFunc = new JTabbedPane();

        /////////////////////////////// Pannello crea ///////////////////////////////////////////
        JPanel creaPanel = new JPanel();
        creaPanel.setLayout(new BoxLayout(creaPanel, BoxLayout.Y_AXIS));
        JLabel nomeLabel = new JLabel("Inserire il nome dell'evento che si intende creare :");
        JTextField nomeField = new JTextField();
        JLabel postiLabel = new JLabel("Inserire i posti per l'evento che si intende creare :");
        JTextField postiField = new JTextField();
        JButton creaButton = new JButton("Crea Evento");
        creaButton.setActionCommand("crea");
        creaButton.addActionListener(myListener);
        creaButton.setAlignmentX(Panel.CENTER_ALIGNMENT);
        nomeField.setAlignmentX(Panel.CENTER_ALIGNMENT);
        nomeLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        postiField.setAlignmentX(Panel.CENTER_ALIGNMENT);
        postiLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);

        postiField.setPreferredSize(new Dimension(300, 20));
        nomeField.setPreferredSize(new Dimension(300, 20));
        postiField.setMaximumSize(new Dimension(300, 20));
        nomeField.setMaximumSize(new Dimension(300, 20));

        creaPanel.add(nomeLabel);
        creaPanel.add(nomeField);
        creaPanel.add(postiLabel);
        creaPanel.add(postiField);
        creaPanel.add(creaButton);
        tabFunc.addTab("Crea", creaPanel);

        /////////////////////////////// Pannello aggiungi//////////////////////////////////////////////////////////////////////////
        JPanel aggiungiPanel = new JPanel();
        aggiungiPanel.setLayout(new BoxLayout(aggiungiPanel, BoxLayout.Y_AXIS));
        JLabel nomeAddLabel = new JLabel("Inserire il nome dell'evento a cui aggiungere i posti :");
        JTextField nomeAddField = new JTextField();
        JLabel postiAddLabel = new JLabel("Inserire il numero di posti da aggiungere :");
        JTextField postiAddField = new JTextField();
        JButton aggiungiButton = new JButton("Aggiungi posti");
        aggiungiButton.setActionCommand("aggiungi");
        aggiungiButton.addActionListener(myListener);

        aggiungiButton.setAlignmentX(Panel.CENTER_ALIGNMENT);
        nomeAddField.setAlignmentX(Panel.CENTER_ALIGNMENT);
        nomeAddLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        postiAddField.setAlignmentX(Panel.CENTER_ALIGNMENT);
        postiAddLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);
        postiAddField.setPreferredSize(new Dimension(300, 20));
        nomeAddField.setPreferredSize(new Dimension(300, 20));
        postiAddField.setMaximumSize(new Dimension(300, 20));
        nomeAddField.setMaximumSize(new Dimension(300, 20));

        aggiungiPanel.add(nomeAddLabel);
        aggiungiPanel.add(nomeAddField);
        aggiungiPanel.add(postiAddLabel);
        aggiungiPanel.add(postiAddField);
        aggiungiPanel.add(aggiungiButton);
        tabFunc.add("Aggiungi", aggiungiPanel);

        /////////////////////////////// Pannello chiudi//////////////////////////////////////////////////////////////////////////
        JPanel chiudiPanel = new JPanel();
        chiudiPanel.setLayout(new BoxLayout(chiudiPanel, BoxLayout.Y_AXIS));
        JLabel nomeRemLabel = new JLabel("Inserire il nome dell'evento da chiudere :");
        JTextField nomeRemField = new JTextField();

        JButton chiudButton = new JButton("Chiudi Evento");
        chiudButton.setActionCommand("chiudi");
        chiudButton.addActionListener(myListener);
        chiudButton.setAlignmentX(Panel.CENTER_ALIGNMENT);
        nomeRemField.setAlignmentX(Panel.CENTER_ALIGNMENT);
        nomeRemLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);

        nomeRemField.setPreferredSize(new Dimension(300, 20));
        nomeRemField.setMaximumSize(new Dimension(300, 20));

        chiudiPanel.add(nomeRemLabel);
        chiudiPanel.add(nomeRemField);
        chiudiPanel.add(chiudButton);
        tabFunc.add("Chiudi", chiudiPanel);

        /////////////////////////////// Pannello output//////////////////////////////////////////////////////////////////////////
        JPanel outPanel = new JPanel();
        outPanel.setLayout(new BoxLayout(outPanel,BoxLayout.PAGE_AXIS));
        JButton stampa = new JButton("Stampa lista eventi");
        stampa.setActionCommand("stampa");
        stampa.addActionListener(myListener);
        JTextArea eventBox = new JTextArea();
        JScrollPane scrollableEventBox = new JScrollPane(eventBox);
        scrollableEventBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        eventBox.setEditable(false);
        eventBox.append("Premere il bottone sottostante per stampare la lista degli eventi...");
        scrollableEventBox.setAlignmentX(Panel.CENTER_ALIGNMENT);
        outPanel.add(scrollableEventBox);
        
        stampa.setAlignmentX(Panel.CENTER_ALIGNMENT);
        outPanel.add(stampa);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabFunc, BorderLayout.NORTH);
        getContentPane().add(new JSeparator());
        getContentPane().add(outPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);

    }

    public static void main(String[] args) {

        Runnable init = new Runnable() {
            public void run() {
                new AdminFrame();
            }
        };
        // creo la GUI nell’EDT
        SwingUtilities.invokeLater(init);
    }
}
