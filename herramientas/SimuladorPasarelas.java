import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class SimuladorPasarelas {

    public static void main(String[] args) throws IOException {
        boolean rechazar = args.length > 0 && args[0].equalsIgnoreCase("rechazar");

        crear(9001, "/pagosudes/transacciones", rechazar
            ? "{\"idTransaccion\":\"PU-0000\",\"estadoTransaccion\":\"RECHAZADA\"}"
            : "{\"idTransaccion\":\"PU-1001\",\"estadoTransaccion\":\"APROBADA\"}");

        crear(9002, "/wompi/transactions", rechazar
            ? "{\"reference\":\"WP-0000\",\"status\":\"DECLINED\"}"
            : "{\"reference\":\"WP-2001\",\"status\":\"APPROVED\"}");

        System.out.println("Simulador activo (" + (rechazar ? "RECHAZA" : "APRUEBA")
            + ") en puertos 9001 y 9002. Ctrl+C para detener.");
    }

    private static void crear(int puerto, String ruta, String respuesta) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(puerto), 0);
        servidor.createContext(ruta, exchange -> {
            exchange.getRequestBody().readAllBytes();
            byte[] cuerpo = respuesta.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, cuerpo.length);
            exchange.getResponseBody().write(cuerpo);
            exchange.close();
        });
        servidor.start();
    }
}