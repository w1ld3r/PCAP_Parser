import java.util.HashMap;

public class DHCPParameterRequestConstants {

    public final static HashMap<Integer, String> DHCP_PARAMETER_REQUEST = new HashMap<Integer, String>();
    
    static
    {
        DHCP_PARAMETER_REQUEST.put(1     ,"Subnet Mask"  );
        DHCP_PARAMETER_REQUEST.put(2     ,"Time Offset"  );
        DHCP_PARAMETER_REQUEST.put(3     ,"Router"       );
        DHCP_PARAMETER_REQUEST.put(4     ,"Time Server"  );
        DHCP_PARAMETER_REQUEST.put(5     ,"Name Server"  );
        DHCP_PARAMETER_REQUEST.put(6     ,"Domain Server");
        DHCP_PARAMETER_REQUEST.put(7     ,"Log Server"   );
        DHCP_PARAMETER_REQUEST.put(8     ,"Quotes Server");
        DHCP_PARAMETER_REQUEST.put(42     ,"NTP Servers" );
    }
}