package ru.mpei;

import jade.core.Agent;
import ru.mpei.Behavior.AcceptLeaderBehavior;
import ru.mpei.Behavior.AcceptRequestToCount;
import ru.mpei.Behavior.SuicideBehavior;


public class FunctionAgent extends Agent {

    @Override

    protected void setup() {
        super.setup();
        String argument = (String) this.getArguments()[0];
        boolean isFirstLeader = Boolean.parseBoolean(argument);
        System.out.println("Hello World. I’m an agent "+getLocalName()+"!");
        this.addBehaviour(new AcceptLeaderBehavior(isFirstLeader));
        this.addBehaviour(new AcceptRequestToCount());
        this.addBehaviour(new SuicideBehavior());
        System.out.println("Агент "+this.getLocalName()+" подключился к матрице");
    }

}
