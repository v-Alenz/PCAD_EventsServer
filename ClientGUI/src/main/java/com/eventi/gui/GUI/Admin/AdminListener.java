package com.eventi.gui.GUI.Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminListener implements ActionListener {

    private AdminFrame frame;
    private AdminWorker worker;

    public AdminListener(AdminFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        switch (command) {
            case "chiudi":
                chiudi();
                break;
            case "stampa":
                stampa();
                break;

            case "aggiungi":
                aggiungi();
                break;

            case "crea":
                crea();
                break;
            default:
                System.out.println("Errore brutto");
        }

    }

    private void chiudi() {

        String nome = frame.nomeRemField.getText();
        if (nome.equals(""))
            return;

        worker = new AdminWorker(null, nome, frame);
        worker.setCommand("chiudi");

        worker.execute();

    }

    private void aggiungi() {
        String nome = frame.nomeAddField.getText();
        String posti = frame.postiAddField.getText();
        if (nome.equals("") || posti.equals(""))
            return;

        worker = new AdminWorker(posti, nome, frame);
        worker.setCommand("aggiungi");
        worker.execute();
    }

    private void crea() {
        String nome = frame.nomeField.getText();
        String posti = frame.postiField.getText();
        if (nome.equals("") || posti.equals(""))
            return;

        worker = new AdminWorker(posti, nome, frame);
        worker.setCommand("crea");
        worker.execute();
    }

    private void stampa() {
        worker = new AdminWorker(null, null, frame);
        worker.setCommand("stampa");
        worker.execute();
    }

}
