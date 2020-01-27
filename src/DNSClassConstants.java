import java.util.HashMap;

public class DNSClassConstants {

    public final static HashMap<Integer, String> DNS_CLASS = new HashMap<Integer, String>();
    
    static
    {
        DNS_CLASS.put(1     ,"IN"   );
        DNS_CLASS.put(3     ,"CH"   );
        DNS_CLASS.put(4     ,"HS"   );
        DNS_CLASS.put(255   ,"ANY"  );
    }
}