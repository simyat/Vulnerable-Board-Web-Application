package Controller;

import model.AddressDTO;
import model.UserDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

@WebServlet("/searchprocess")
public class SearchAddressController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObj;
        JSONArray jsonArr;
        String address = req.getParameter("address");

        UserDAO dao = new UserDAO();
        ArrayList<AddressDTO> vo_list = dao.SearchAddress(address);

        if (vo_list.size() != 0) {
            jsonObj = new JSONObject();
            jsonArr = new JSONArray();
            HashSet<String> set = new HashSet<>();

            for (int i = 0; i < vo_list.size(); i++) {
                AddressDTO vo = vo_list.get(i);
                String result = vo.getSido() + " " + vo.getSigungu() + " " + vo.getDoro_name() + " "
                        + vo.getSigungu_building_name();

                if (!set.add(result)) {
                    continue;
                }
            }

            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String element = it.next();
                jsonArr.put(element);
            }
            jsonObj.put("search", "success");
            jsonObj.put("address", jsonArr);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(jsonObj.toString());

            out.flush();
        } else {
            jsonObj = new JSONObject();
            jsonObj.put("search", "fail");

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(jsonObj.toString());
            out.flush();
        }
    }
}
