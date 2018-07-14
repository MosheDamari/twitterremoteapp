package Util;


import Objects.TwitterObj;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchResultsServer extends AbstractHandler {
    public static void main(String[] args) throws Exception {
        // Connect to the database
        DataStorage dataStorage = new DataStorage(args[0],args[1],args[2],args[3],args[4]);

        // Start the http server on port 8080
        Server server = new Server(8080);

        server.setHandler(new SearchResultsServer(dataStorage));

        server.start();
        server.join();
    }

    private DataStorage storage;

    SearchResultsServer(DataStorage dataStorage) {
        storage = dataStorage;
    }

    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        // Set the content type to JSON
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        // Set the status to 200 OK
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        // Build data from request
        List<TwitterObj> results = storage.Search(httpServletRequest.getParameter("query"));

        // Notify that this request was handled
        request.setHandled(true);

        // Convert data to JSON string and write to output
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getWriter(), results);
    }
}
