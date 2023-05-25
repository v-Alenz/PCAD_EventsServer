package com.eventi.gui.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

import org.json.JSONObject;

public class Worker extends SwingWorker<String, Void> {

    private String numeroPosti;
    private String nomeEvento;
    private Client frame;
    private Boolean bookFunction;

    public Worker(String evento, String posti, Client frame, Boolean func) {
        this.numeroPosti = posti;
        this.nomeEvento = evento;
        this.frame = frame;
        this.bookFunction = func;
    }

    @Override
    protected String doInBackground() throws Exception {
        if (!isCancelled()) {
            try {

                Socket Socket = new Socket("localhost", 6001);
                BufferedReader Reader = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
                BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(Socket.getOutputStream()));
                String command;
                String crea1 = "{\"role\": \"ADMIN\",\"command\": {\"type\": \"CREATE\",\"name\": \"Concerto Madonna\",\"seats\": 345}}";
                String crea2 = "{\"role\": \"ADMIN\",\"command\": {\"type\": \"CREATE\",\"name\": \"Spettacolo Frozen\",\"seats\": 645}}";
                String crea3 = "{\"role\": \"ADMIN\",\"command\": {\"type\": \"CREATE\",\"name\": \"Mostra \",\"seats\": 575}}";
                String crea4 = "{\"role\": \"ADMIN\",\"command\": {\"type\": \"CREATE\",\"name\": \"Alberobello ma brutto\",\"seats\": 7645}}";
                String crea5 = "{\"role\": \"ADMIN\",\"command\": {\"type\": \"CREATE\",\"name\": \"MOOSECA\",\"seats\": 6345}}";

                if (this.bookFunction) {
                    command = "{\"role\": \"USER\",\"command\": {\"type\": \"BOOK\",\"name\":" + this.nomeEvento
                            + ",\"seats\":" + this.numeroPosti + "}}";
                } else
                    command = "{\"role\": \"USER\",\"command\": {\"type\": \"LIST\"}}";

                Writer.write(command);

                Writer.newLine();
                Writer.flush();
                String recv_buffer = Reader.readLine();
                Reader.close();
                Writer.close();
                Socket.close();
                JSONObject json = new JSONObject(recv_buffer);

                return handleOutput(json);

            } catch (Exception ex) {

                frame.stateLabel.setText("Stato : Operazione fallita");
            }
        }
        return "";

    }

    @Override
    protected void done() {
        frame.prenota.setEnabled(true);
        frame.annulla.setEnabled(false);
        if (isCancelled())
            frame.stateLabel.setText("Stato : Operazione di prenotazione  annullata");
        else {
            if(this.bookFunction)
                frame.stateLabel.setText(" Stato : Operazione di prenotazione completata ");
            else
                frame.stateLabel.setText(" Stato : Operazione di Stampa completata ");
            String result;
            try {
                result = get();
                frame.eventBox.setText(result);
            } catch (InterruptedException e) {

                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    private String handleOutput(JSONObject serverResult) {

        if (serverResult.has("result")) // se non ho un result da guardare sottointendo che sia un comando List
            return this.readBookCommandResult(serverResult);
        else
            return this.readListCommandResult(serverResult);

    }

    private String readBookCommandResult(JSONObject serverResult) {

        String result = serverResult.getString("result");
        switch (result) {
            case "OK":
                return result + ": La prenotazione è andata a buon fine";
            case "ERROR":
                return result + ":  Prenotazione non eseguibile";

        }
        return "";
    }

    private String readListCommandResult(JSONObject serverResult) {

        String listResult = "";
        String nomeEvento;
        Integer maxSeats;
        Integer seats;

        Iterator<String> eventi = serverResult.keys();
        while (eventi.hasNext()) {
            String key = (String) eventi.next();
            maxSeats = serverResult.getJSONObject(key).getInt("max_seats");
            seats = serverResult.getJSONObject(key).getInt("seats");
            nomeEvento = serverResult.getJSONObject(key).getString("name");
            listResult = listResult + "Nome evento : "
                    + nomeEvento + " | Posti massimi : " + maxSeats.toString() + " | Posti disponibili : "
                    + seats.toString() + "\n\n";

        }
        if (listResult != "")
            return listResult;
        else
            return "La lista degli eventi è momentaneamente vuota";
    }
}
