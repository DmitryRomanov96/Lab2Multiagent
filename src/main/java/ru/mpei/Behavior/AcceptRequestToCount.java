package ru.mpei.Behavior;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ru.mpei.CountFunction;

public class AcceptRequestToCount extends Behaviour {

    private MessageTemplate mtDoCount = MessageTemplate.MatchProtocol("DoCount");
    private ACLMessage msg;
    private ACLMessage reply;
    private double x0;
    private double delta;

    String reply1;
    String reply2;
    String reply3;

    @Override
    public void action() {
        msg = myAgent.receive(mtDoCount);
        if (msg != null){
            System.out.println(myAgent.getLocalName()+" получил задание на расчёт");
            String[] result = msg.getContent().split(";");
            this.x0 = Double.parseDouble(result[0]);
            this.delta = Double.parseDouble(result[1]);
            System.out.println(myAgent.getLocalName()+" получил задание на расчёт.\nИсходные данные: \nx0 = "+x0+" delta = "+delta);
            reply = msg.createReply();

            reply.setProtocol("Reply");

            this.reply1 = Double.toString(CountFunction.doCount(x0-delta, myAgent));
            this.reply2 = Double.toString(CountFunction.doCount(x0, myAgent));
            this.reply3 = Double.toString(CountFunction.doCount(x0+delta, myAgent));

            System.out.println(myAgent.getLocalName()+" рассчитал значения: \n" +
                    "x0-delta    = "+this.reply1+"\n"+
                    "x0          = "+this.reply2+"\n"+
                    "x0+delta    = "+this.reply3);

            reply.setContent(reply1+";"+reply2+";"+reply3);
            myAgent.send(reply);
            System.out.println(myAgent.getLocalName()+" отправил письмо с расчётами");
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
