
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/demoRW")
public class JSON extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        String action = req.getParameter("action");

        if (action != null && action.equals("read"))
            read(req, resp);
        else if (action != null && action.equals("write"))
            write(req, resp);
    }

    private void read(HttpServletRequest req, HttpServletResponse resp) {
        //SingleListPersons listQA = SingleListPersons.getInstance();
        SingleListPersons listQA = new SingleListPersons();

        JSONObject json = new JSONObject();
        try {
            json.put("pers", listQA.getListOfNames());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        returnJsonResponse(resp, json.toString());
    }

    private void write(HttpServletRequest req, HttpServletResponse resp) {

        String que = req.getParameter("newName");
        String res = req.getParameter("r");
        IR ir = new IR(que, res);

        SingleListPersons listOfNames = new SingleListPersons();
        try {
            listOfNames.addInTheListOfNames(ir);
        } catch (ClassNotFoundException e) {

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }
}
