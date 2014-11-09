package WorkStop;

import UserManagement.UserLogic;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

/**
 * Created by Martin on 11/8/2014.
 */
public class Work extends ServerResource {
    @Get
    public String challenge()
    {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        if(UserLogic.validUser(getQuery().getValues("id")) != null)
        {
            if(getQuery().getValues("clear") != null && getQuery().getValues("clear").equalsIgnoreCase("true")) {
                String response = getQuery().getValues("id") + " work stopped clear";
                WorkLogic.start(getQuery().getValues("id"));
                return response;
            }
            else
            {
                if(UserLogic.getWork(getQuery().getValues("id")) == null)
                {
                    return "-1";
                }
                 return String.valueOf(UserLogic.getWork(getQuery().getValues("id")).id());
            }
        }
        return "-1";
    }
}
