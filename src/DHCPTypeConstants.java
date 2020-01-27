import java.util.HashMap;

public class DHCPTypeConstants {

    public final static HashMap<Integer, String> DHCP_TYPE = new HashMap<Integer, String>();
    
    static
    {
        DHCP_TYPE.put(1     ,"Discover"     );
        DHCP_TYPE.put(2     ,"Offer"        );
        DHCP_TYPE.put(3     ,"Request"      );
        DHCP_TYPE.put(4     ,"Decline"      );
        DHCP_TYPE.put(5     ,"ACK"          );
        DHCP_TYPE.put(6     ,"Negative"     );
        DHCP_TYPE.put(7     ,"Release"      );
        DHCP_TYPE.put(8     ,"Informational");
    }
}