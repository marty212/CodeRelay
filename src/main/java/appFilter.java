import WorkStop.Work;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import UserManagement.*;
import Challenges.*;

/**
 * Created by Martin on 11/8/2014.
 */
public class appFilter extends Application {
    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/receive/", validateChallenge.class);
        router.attach("/new/", newUser.class);
        router.attach("/challenges/", ChallengeManager.class);
        router.attach("/work/", Work.class);
        return router;
    }
}