package ru.mpei.Behavior;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import ru.mpei.CountFunction;

import java.util.ArrayList;
import java.util.List;

public class SendRequestToCount extends OneShotBehaviour {

    private List<AID> listOfAgents = new ArrayList<>();
    private double delta;
    private double x0;

    public SendRequestToCount(double delta, double x0) {
        this.delta = delta;
        this.x0 = x0;
    }

    @Override
    public void action() {
        if(listOfAgents.isEmpty()){
            listOfAgents = CountFunction.addReceivers(myAgent);
        }
        ACLMessage message = new ACLMessage();
        message.setProtocol("DoCount");
        message.setContent(x0+";"+delta);
        for (AID agent: this.listOfAgents){
            message.addReceiver(agent);
        }
        myAgent.send(message);
        System.out.println(myAgent.getLocalName()+" отправил письма");
        myAgent.addBehaviour(new ReceiveAndCompareResults(this.x0, this.delta));
    }


}
