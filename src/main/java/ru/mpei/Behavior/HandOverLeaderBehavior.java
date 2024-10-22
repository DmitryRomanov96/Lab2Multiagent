package ru.mpei.Behavior;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class HandOverLeaderBehavior extends OneShotBehaviour {

    private double x0;
    private double delta;

    public HandOverLeaderBehavior(double x0, double delta) {
        this.x0 = x0;
        this.delta = delta;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage();
        msg.setProtocol("newLeader");
        msg.setContent(x0 +";"+ delta);
        msg.addReceiver(this.findReceiver());
        myAgent.send(msg);
        System.out.println(myAgent.getLocalName()+ " передаёт роль лидера "+this.findReceiver().getLocalName());
    }

    private AID findReceiver(){
        if (myAgent.getLocalName().equals("Smith")){
            return new AID("Brown", false);
        }
        else if (myAgent.getLocalName().equals("Brown")){
            return new AID("Jones", false);
        }
        else {
            return new AID("Smith", false);
        }
    }

}
