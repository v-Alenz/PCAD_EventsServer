package com.eventi.gui.GUI;

import java.awt.event.*;

public class Listener implements ActionListener {
    private Client frame;
    private Worker worker;

    public Listener(Client frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "stampa") {
            stampa();
        } else if (command == "prenota") {
            prenota();
        } else
            annulla();
    }

    private void prenota() {
        String evento = frame.eventoField.getText();
        String posti = frame.postiField.getText();
        if (evento.equals("") || posti.equals("")) {
            return;
        }
        frame.prenota.setEnabled(false);
        frame.annulla.setEnabled(true);
        frame.stateLabel.setText("Stato : Prenotazione in corso...");
        worker = new Worker(evento, posti, frame, true);

        worker.execute();
    }

    private void stampa() {
        frame.stateLabel.setText(" Stato : Stampa eventi in corso... ");
        worker = new Worker(null, null, frame, false);

        worker.execute();
    }


    private void annulla(){
        this.worker.cancel(true);
    }
}
