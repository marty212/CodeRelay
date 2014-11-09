import UserManagement.UserLogic;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;

/**
 * Created by Martin on 11/8/2014.
 */
public class app extends ServerResource {
    public static void main(String args[]) throws Exception
    {
        Component component = new Component();
        Server server = component.getServers().add(Protocol.HTTP, 8182);
        server.getContext().getParameters().add("useForwardedForHeader", "true");
        component.getDefaultHost().attach(new appFilter());
        UserLogic.init();
        component.start();
    }
}
