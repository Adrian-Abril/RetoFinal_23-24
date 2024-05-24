package Controller;

import Controller.Actions.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        //request.getMethod()
        //request.getQueryString()
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String strAction = request.getParameter("ACTION");
        //ACTION=PELICULA.FIND_ALL --> HAMBURGUER.FIND_ALL // USER.FIND
        String[] arrayAction= new String[2];;
        if (strAction != "")
        {
            arrayAction = strAction.split("\\."); //[0] PELICULA <-> [1] FIND_ALL
        }
        switch (arrayAction[0].toUpperCase())
        {
            case "PRODUCTO":
            {
                out.print(new ProductoAction().execute(request,response, arrayAction[1]));
                break;
            }

            /*case "EMPLEADOS":
            {
                out.print(new EmpleadosAction().execute(request,response, arrayAction[1]));
                break;
            }
            case "CLIENTES":
            {
                out.print(new ClientesAction().execute(request,response, arrayAction[1]));
                break;
            }
            case "TIPOS":
            {
                out.print(new TiposAction().execute(request,response, arrayAction[1]));
                break;
            }

            case "METODO_PAGO":
            {
                out.print(new Metodo_PagoAction().execute(request,response, arrayAction[1]));
                break;
            }
            case "PEDIDOS":
            {
                out.print(new PedidosAction().execute(request,response, arrayAction[1]));
                break;
            }*/
            default:
            {
                System.out.println(arrayAction[0]);
                throw new ServletException ("Acción " + arrayAction[0] +" no valida");
            }
        }
        System.out.println(strAction);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}