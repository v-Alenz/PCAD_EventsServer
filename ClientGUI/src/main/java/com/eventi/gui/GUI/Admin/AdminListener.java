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
        switch(command){
            case "crea": 
                crea();
            case "aggiungi":
                aggiungi();
            case "chiudi":
                chiudi();
        }  
    }

    private void chiudi() {
    }

    private void aggiungi() {
    }

    private void crea() {
    }

}
