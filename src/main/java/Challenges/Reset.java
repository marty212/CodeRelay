package Challenges;

import UserManagement.UserLogic;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

/**
 * Created by Martin on 11/8/2014.
 */
public class Reset extends ServerResource {

    @Get
    public String reset()
    {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        UserLogic.reset(getQuery().getValues("id"));
        return "done";
    }
}
