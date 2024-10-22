package ru.mpei.Behavior;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ru.mpei.CountFunction;

public class ReceiveAndCompareResults extends Behaviour {

    private int countedMessages = 0;
    private double x0;
    private double delta;

    private double funSumX0;
    private double funSumX0PlusDelta;
    private double funSumX0MinusDelta;

    private double result1 = 0;
    private double result2 = 0;
    private double result3 = 0;

    private boolean deltaIsChanged = false;

    private ACLMessage msg;
    private MessageTemplate MT = MessageTemplate.MatchProtocol("Reply");

    public ReceiveAndCompareResults(double x0, double delta) {
        this.x0 = x0;
        this.delta = delta;
        this.funSumX0 = 0;
        this.funSumX0MinusDelta = 0;
        this.funSumX0PlusDelta = 0;
    }

    @Override
    public void action() {
        msg = myAgent.receive(MT);
        if (msg != null) {
            String[] result = msg.getContent().split(";");
            System.out.println(myAgent.getLocalName()+" получил сообщение от "+msg.getSender().getLocalName()+"\n"
                    +"Результат его расчёта: "+Double.parseDouble(result[0])+";"+Double.parseDouble(result[1])+";"+Double.parseDouble(result[2]));
            this.funSumX0MinusDelta += Double.parseDouble(result[0]);
            this.funSumX0 += Double.parseDouble(result[1]);
            this.funSumX0PlusDelta += Double.parseDouble(result[2]);
            countedMessages += 1;
            if (countedMessages == 2){
                this.result1 = CountFunction.doCount(this.x0-this.delta,myAgent);
                this.result2 = CountFunction.doCount(this.x0,myAgent);
                this.result3 = CountFunction.doCount(this.x0+this.delta,myAgent);

                System.out.println(myAgent.getLocalName()+" расчитал свои значения: \n" +
                        "f-d = "+this.result1+"\nf = "+result2+"\nf+d = "+result3);

                this.funSumX0MinusDelta += this.result1;
                this.funSumX0 += this.result2;
                this.funSumX0PlusDelta += this.result3;
                this.compareResults();
                if(!deltaIsChanged){myAgent.addBehaviour(new HandOverLeaderBehavior(this.x0, this.delta));}
                else {
                    myAgent.addBehaviour(new SendRequestToCount(delta, x0));
                    System.out.println(myAgent.getLocalName()+" остаётся лидером");
                    this.deltaIsChanged = false;
                }
                this.funSumX0 = 0;
                this.funSumX0MinusDelta = 0;
                this.funSumX0PlusDelta = 0;
            }
        } else {
            block();
        }
}


    @Override
    public boolean done() {
        return countedMessages == 2;
    }

    private void compareResults(){
        System.out.println("Результаты расчёта: \n"+
                "f-d = "+this.funSumX0MinusDelta+"; f = "+this.funSumX0+"; f+d = "+this.funSumX0PlusDelta);
        if (this.funSumX0MinusDelta < this.funSumX0 && this.funSumX0MinusDelta < this.funSumX0PlusDelta){
            this.x0 -= this.delta;
            System.out.println(myAgent.getLocalName()+" сдвигает x0 влево; x0 = "+x0);
        } else if (this.funSumX0PlusDelta < this.funSumX0 && funSumX0PlusDelta < this.funSumX0MinusDelta) {
            this.x0 += this.delta;
            System.out.println(myAgent.getLocalName()+" сдвигает x0 вправо; x0 = "+x0);
        }
        else {
            this.delta = this.delta/2;
            this.deltaIsChanged = true;
            System.out.println(myAgent.getLocalName()+" остаётся на месте; delta = "+delta);
        }
    }

}
