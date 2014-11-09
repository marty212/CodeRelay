package Challenges;

import UserManagement.UserLogic;
import WorkStop.WorkLogic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

/**
 * Created by Martin on 11/8/2014.
 */
public class ChallengeManager extends ServerResource {

    @Get
    public String getChallenge()
    {
        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));

        String response = "{\"problem\":";

        Problem p = UserLogic.getProblem(getQuery().getValues("id"));

        if(p != null)
        {
            return response + "\"" + p.getProblem() + "\"}";
        }

        String problem = UserLogic.setProblem(getQuery().getValues("id"), getQuery().getValues("challenger"), JavaChallenge.getNewProblem());

        if(problem == null)
        {
            response += "\"something went wrong\"}";
            return response;
        }

        response += "\"" + problem + "\"}";

        return response;
    }

    @Post
    public String validate(Representation entity) throws Exception
    {

        Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(entity.getText());

        String id = rootNode.path("id").asText();
        if(UserLogic.validUser(id) == null)
        {
            return Boolean.toString(false);
        }

        boolean good = JavaChallenge.validateProblem(rootNode.path("message").asText(), UserLogic.getProblem(id));

        if(UserLogic.done(id))
        {
            return "you lost";
        }
        else if(good && UserLogic.setAsWinner(id))
        {
            return "you won";
        }
        else if(UserLogic.stopped(id) != null)
        {
            return "you are currently under a work stop, complete the challenge";
        }
        else
        {
            WorkLogic.stop(id);
            return "nope";
        }
    }
}
