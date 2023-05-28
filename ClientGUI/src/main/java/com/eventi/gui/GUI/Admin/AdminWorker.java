package com.eventi.gui.GUI.Admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.json.JSONObject;

public class AdminWorker extends SwingWorker<String, Void> {

    private String numeroPosti;
    private String nomeEvento;
    private AdminFrame frame;
    public String command;

    public AdminWorker(String posti, String evento, AdminFrame frame) {
        this.nomeEvento = evento;
        this.numeroPosti = posti;
        this.frame = frame;
       
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    protected String doInBackground() throws Exception {
        
        try {
            
            Socket Socket = new Socket("localhost", 6001);
            BufferedReader Reader = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
            BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(Socket.getOutputStream()));


            String command="";
            String crea= "{\"role\": \"ADMIN\",\"command\": {\"type\": \"CREATE\",\"name\":" + this.nomeEvento
            + ",\"seats\":" + this.numeroPosti + "}}";

            String aggiungi = "{\"role\": \"ADMIN\",\"command\": {\"type\": \"ADD\",\"name\":" + this.nomeEvento
            + ",\"seats\":" + this.numeroPosti + "}}";

            String chiudi = "{\"role\": \"ADMIN\",\"command\": {\"type\": \"CLOSE\",\"name\":" + this.nomeEvento + "}}";

            String stampa = "{\"role\": \"USER\",\"command\": {\"type\": \"LIST\"}}";
            
            
            switch (this.command) {

                case "crea": 
                        command= crea;
                        break;
                case "aggiungi": 
                    command = aggiungi;
                    break;
                case "chiudi":  
                    command= chiudi;
                     break;
                   case "stampa": 
                    command= stampa;
                     break;

                default : System.out.println("Errore fatale");
            }
            
            
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

            ex.printStackTrace();
        }
        return "";

    }

    private String handleOutput(JSONObject serverResult) {

        if (serverResult.has("result")) // se non ho un result da guardare sottointendo che sia un comando List
            return this.readOperationResult(serverResult);
        else
            return this.readListCommandResult(serverResult);

    }

    private String readOperationResult(JSONObject serverResult) {

        String result = serverResult.getString("result");
        switch (result) {
            case "OK":
                return result + ": L'operazione è andata a buon fine";
            case "ERROR":
                return result + ":  Operazione non eseguibile";

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



    @Override
    protected void done() {
      
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
