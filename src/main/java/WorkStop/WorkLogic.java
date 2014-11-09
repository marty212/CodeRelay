package WorkStop;

import UserManagement.UserLogic;

/**
 * Created by Martin on 11/8/2014.
 */
public class WorkLogic {

    public static void stop(String owner)
    {
        WorkStop stop = new WorkStop() {
            @Override
            public String challenge() {
                return "run 100 meters";
            }

            public int id()
            {
                return 0;
            }
        };
        UserLogic.setWork(owner, stop);
    }

    public static void start(String owner)
    {
        UserLogic.setWork(owner, null);
    }
}
