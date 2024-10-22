package ru.mpei;

import jade.core.AID;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

public class CountFunction {

    public static double doCount(double x, Agent agent){
        if (agent.getLocalName().equals("Jones")){
            return Math.exp(0.3*x);
        } else if (agent.getLocalName().equals("Brown")) {
            return Math.pow(x, 2);
        }
        else if (agent.getLocalName().equals("Smith")){
            return Math.sin(x);
        }
        else {
            throw new RuntimeException("Got unknown agent");
        }
    }

    public static List<AID> addReceivers(Agent agent){
        List<AID> listOfAgents = new ArrayList<>();
        if (agent.getLocalName().equals("Smith")){
            listOfAgents.add(new AID("Brown", false));
            listOfAgents.add(new AID("Jones", false));
        } else if (agent.getLocalName().equals("Brown")) {
            listOfAgents.add(new AID("Smith", false));
            listOfAgents.add(new AID("Jones", false));
        }
        else {
            listOfAgents.add(new AID("Smith", false));
            listOfAgents.add(new AID("Brown", false));
        }
        return listOfAgents;
    }

}
