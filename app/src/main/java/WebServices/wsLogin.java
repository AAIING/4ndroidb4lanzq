package WebServices;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class wsLogin {

    private Context context;

    private static final String PREFER_NAME = "xxxxxxxxxxx";
    private SharedPreferences sharedPreferences;
    private final String SOAP_ACTION = "http://tempuri.org/Login";
    private final String OPERATION_NAME = "Login";
    private  final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private  final String SOAP_ADDRESS = "http://wsxxxxxxxxxxx.cl/WebServicexxxxxxxx.asmx";

    public wsLogin(Context context) {
        this.context = context;
    }

    public String LoginMovil(String a,String b) {
        Log.e("Usuario",a);
        Log.e("Password",b);
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("usuario");
        pi.setValue(a);
        pi.setType(String.class);
        request.addProperty(pi);
        pi=new PropertyInfo();
        pi.setName("password");
        pi.setValue(b);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

        try
        {
            httpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive  response = (SoapPrimitive)envelope.getResponse();
            String res=response.toString();
            if(res.equals("ok") )
            {return "2";}
            else
            {return res;}
        }
        catch (Exception errores)
        {Log.e("wserrores", String.valueOf(errores));
            return String.valueOf(errores);
        }

    }
}