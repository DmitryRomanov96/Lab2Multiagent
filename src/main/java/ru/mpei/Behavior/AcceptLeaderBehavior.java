package ru.mpei.Behavior;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ru.mpei.CountFunction;

import java.util.List;

public class AcceptLeaderBehavior extends Behaviour {

    private boolean isFirstLeader;

    private MessageTemplate MT = MessageTemplate.MatchProtocol("newLeader");
    private ACLMessage msg;

    public AcceptLeaderBehavior(boolean isFirstLeader) {
        this.isFirstLeader = isFirstLeader;
    }

    @Override
    public void action() {
        if (isFirstLeader){this.actionIfFirstLeader();}
        msg = myAgent.receive(MT);
        if (msg != null){
            System.out.println(myAgent.getLocalName()+" получил письмо и стал лидером");
            String[] result = msg.getContent().split(";"); // 0 - x0, 1 - delta
            double x0 = Double.parseDouble(result[0]);
            double delta = Double.parseDouble(result[1]);
            if (delta <= 0.01){
                System.out.println("=========================================");
                List<AID> listOfReceivers = CountFunction.addReceivers(myAgent);
                for (AID agent: listOfReceivers){
                    msg.addReceiver(agent);
                }
                System.out.println("Global min is: "+x0);
                msg.setProtocol("killYourself");
                msg.setContent("killYourself");
                myAgent.send(msg);
                System.out.println("Агент "+myAgent.getLocalName()+" велел всем себя убить");
                myAgent.doDelete();
            }
            else {
                myAgent.addBehaviour(new SendRequestToCount(delta, x0));
            }
        }
        else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    private void actionIfFirstLeader(){
        double randX0 = 10 - 20 * Math.random();
        myAgent.addBehaviour(new SendRequestToCount(1, randX0));
        this.isFirstLeader = false;
    }

}
