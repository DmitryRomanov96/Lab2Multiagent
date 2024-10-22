package ru.mpei.Behavior;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SuicideBehavior extends Behaviour {

    private MessageTemplate mt = MessageTemplate.MatchProtocol("killYourself");
    private ACLMessage msg;

    @Override
    public void action() {
        msg = myAgent.receive(mt);
        if (msg != null){
            System.out.println(myAgent.getLocalName()+" покинул беседу");
            myAgent.doDelete();
        }
        else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
