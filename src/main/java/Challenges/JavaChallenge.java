package Challenges;

import bsh.Interpreter;

import java.util.Random;

/**
 * Created by Martin on 11/8/2014.
 */
public class JavaChallenge {

    public static void main(String args[])
    {
        String str = "public String toString(int i){ return String.valueOf(i);}";
        System.out.println(validateProblem(str, challenge.JAVA_ZERO.getProblem()));
    }
    protected enum challenge
    {
        JAVA_ZERO(0,new Input[] {new Input(new String[]{"1"}, "1"), new Input(new String[]{"\"2\""}, "2")}, "echo a parameter passed in to the method called toString(String str)", "toString"),
        JAVA_ONE(1,new Input[] {new Input(new String[]{"1", "2"}, "3"), new Input(new String[]{"3", "4"}, "7")}, "add two numbers together in method called add(int i, int j)", "add"),
        JAVA_TWO(2,new Input[] {new Input(new String[]{"2"}, "2"), new Input(new String[]{"3"}, "6")}, "calculate n! with a method called fact(int n)", "fact");

        Problem p;

        challenge(int id, Input[] anwser, String problem, String method)
        {
            p = new Problem();
            p.answers = anwser;
            p.problemStatement = problem;
            p.className = method;
            p.id = id;
        }

        public Problem getProblem()
        {
            return p;
        }
    }

    public static boolean validateProblem(final String str, final Problem p)
    {
        final Result r = new Result();
        //validateProblem(str, p, r);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                validateProblem(str, p, r);
            }
        });
        t.start();
        try {
            t.join(15000);
        }
        catch(Exception e)
        {
            //throw away
        }
        t.stop();//to kill infinite loops
        return r.good;
    }

    private static void validateProblem(String str, Problem p, Result r)
    {

        Interpreter interpreter = new Interpreter();
        try {

            interpreter.eval(str);
            for(Input i : p.getAnswers()) {
                String params = "";
                for(String s : i.params) {
                    if (!params.isEmpty()) {
                        params += ",";
                    }
                    params += s;
                }
                //System.out.println(p.className + "(" + params + ");");
                String s = String.valueOf(interpreter.eval(p.getClassName() + "(" + params + ");"));
                //System.out.println("expected: " + i.output + " got: " + s);
                if(!i.output.equals(s))
                {
                    r.good = false;
                    return;
                }
            }
            r.good = true;
        }
        catch(Exception e)
        {
            r.good = false;
        }
    }

    public static Problem getNewProblem()
    {
        Random r = new Random();
        switch (r.nextInt(3))
        {
            case 0 : return challenge.JAVA_ZERO.getProblem();
            case 1 : return challenge.JAVA_ONE.getProblem();
            case 2 : return challenge.JAVA_TWO.getProblem();
        }
        return null;
    }
}
