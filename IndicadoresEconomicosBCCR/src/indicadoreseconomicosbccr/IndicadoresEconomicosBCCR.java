/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indicadoreseconomicosbccr;

/**
 *
 * @author SilviaElena
 */
public class IndicadoresEconomicosBCCR {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        WebServiceBCCR ws = new WebServiceBCCR();
        ws.obtenerRespuesta();
        System.out.println(ws.obtenerTipoCambio());
    }
    
}
