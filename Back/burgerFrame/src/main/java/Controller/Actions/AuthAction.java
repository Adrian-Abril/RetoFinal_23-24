package Controller.Actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Cliente;
import Model.DAO.ClienteDao;
import com.google.gson.Gson;
import java.io.IOException;

public class AuthAction implements IAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action.toUpperCase()) {
            case "LOGIN":
                strReturn = login(request, response);
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String login(HttpServletRequest request, HttpServletResponse response) {
        String emailOrPhone = request.getParameter("username");
        String password = request.getParameter("password");

        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = clienteDao.findByEmailOrPhoneAndPassword(emailOrPhone, password);

        Gson gson = new Gson();
        String jsonResponse;

        if (cliente != null) {
            jsonResponse = gson.toJson(new ResponseMessage(true, "Login successful"));
        } else {
            jsonResponse = gson.toJson(new ResponseMessage(false, "Invalid credentials"));
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    class ResponseMessage {
        private boolean success;
        private String message;

        public ResponseMessage(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
