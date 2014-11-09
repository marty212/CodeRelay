package UserManagement;

import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;


/**
 * Created by Martin on 11/8/2014.
 */
public class validateChallenge extends ServerResource {

    @Get
    public Object getRes()
    {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        String response = "{\"valid\":";
        if(UserLogic.validUser(getQuery().getValues("id")) != null)
        {
            if(getQuery().getValues("challenger") != null && UserLogic.setChallenger(getQuery().getValues("id"), getQuery().getValues("challenger")))
            {
                response += "true, \"challengeSet\":true}";
            }
            else
            {
                response += "true, \"challengeSet\":false}";
            }
        }
        else
        {
            response += "false, \"challengeSet\":false}";
        }
        return response;
    }
}
