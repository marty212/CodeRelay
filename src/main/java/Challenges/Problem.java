package Challenges;

/**
 * Created by Martin on 11/8/2014.
 */
public class Problem {
    protected Input answers[];
    protected String problemStatement;
    protected String className;
    protected int id;

    public Input[] getAnswers()
    {
        return answers;
    }//format {param1,param2}output1{param1,param2}output2
    public String getProblem()
    {
       return problemStatement;
    }
    public String getClassName()
    {
        return className;
    }

    public int getId()
    {
        return id;
    }
}
