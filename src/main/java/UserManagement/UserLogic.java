package UserManagement;

import Challenges.Problem;
import WorkStop.WorkStop;

import java.util.ArrayList;

/**
 * Created by Martin on 11/8/2014.
 */
public class UserLogic {

    private static ArrayList<User> userlist;
    private static int i;
    private static int challengeid;

    public static void init()
    {
        userlist = new ArrayList<User>();
        i = 0;
        challengeid = 42;
    }

    public static String newUser()
    {
        User use = new User();
        use.id = String.valueOf(i++);
        use.challenger = String.valueOf(challengeid);
        userlist.add(use);
        return use.id;
    }

    public static User validUser(String id)
    {
        if(id == null)
        {
            return null;
        }
        for(User use : userlist)
        {
            if(use.id.equals(id))
            {
                return use;
            }
        }
        return null;
    }

    public static boolean reset(String owner)
    {
        User own = validUser(owner);
        if(own != null)
        {
            own.challenger = null;
            own.won = false;
            own.done = false;
            own.p = null;
            own.work = null;
        }
        return false;
    }

    public static boolean setChallenger(String owner, String challenger)
    {
        User own = validUser(owner);
        User challenge = validUser(challenger);
        if(own != null && challenge != null)
        {
            own.challenger = challenger;
            challenge.challenger = owner;
            return true;
        }
        return false;
    }

    public static Problem getProblem(String id)
    {
        if(validUser(id) == null)
        {
            return null;
        }
        return validUser(id).p;
    }

    public static String setProblem(String owner, String challenger, Problem p)
    {
        if(validUser(owner) == null || validUser(challenger) == null)
        {
            return null;
        }
        validUser(owner).p = p;
        validUser(challenger).p = p;
        return p.getProblem();
    }

    public static boolean won(String owner)
    {
        if(validUser(owner) == null)
        {
            return false;
        }
        return validUser(owner).won;
    }

    public static boolean setAsWinner(String owner)
    {
        if(validUser(owner) == null)
        {
            return false;
        }
        validUser(owner).won = true;
        validUser(validUser(owner).challenger).done = true;
        validUser(owner).done = true;
        return true;
    }

    public static boolean done(String owner)
    {
        if(validUser(owner) == null)
        {
            return false;
        }
        return validUser(owner).done;
    }

    public static WorkStop stopped(String owner)
    {
        if(validUser(owner) == null)
        {
            return null;
        }
        return validUser(owner).work;
    }

    public static void setWork(String owner, WorkStop work)
    {
        if(validUser(owner) == null)
        {
            return;
        }
        validUser(owner).work = work;
    }

    public static WorkStop getWork(String owner)
    {
        if(validUser(owner) == null)
        {
            return null;
        }
        return validUser(owner).work;
    }

    protected static class User
    {
        protected String id;
        protected String challenger;
        protected Problem p;
        protected boolean won = false;
        protected boolean done = false;
        public WorkStop work;
    }
}
