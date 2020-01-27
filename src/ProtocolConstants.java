import java.util.HashMap;

// Constants link layer enum
public class ProtocolConstants {
	
	public final static HashMap<Integer, String> PROTOCOL_LIST = new HashMap<Integer, String>();
	
	static
	{
        PROTOCOL_LIST.put(1     ,"ICMP"     );
        PROTOCOL_LIST.put(2     ,"IGMP"     );
        PROTOCOL_LIST.put(6     ,"TCP"      );
        PROTOCOL_LIST.put(17    ,"UDP"      );
        PROTOCOL_LIST.put(41    ,"ENCAP"    );
        PROTOCOL_LIST.put(89    ,"OSPF"     );
        PROTOCOL_LIST.put(132   ,"SCTP"     );
    }
}