import java.util.HashMap;

public class DHCPMessageTypeConstants {

    public final static HashMap<Integer, String> DHCP_MESSAGE_TYPE = new HashMap<Integer, String>();
    
    static
    {
        DHCP_MESSAGE_TYPE.put(1 ,"Boot Request (1)" );
        DHCP_MESSAGE_TYPE.put(2 ,"Boot Reply (2)"   );
    }
}