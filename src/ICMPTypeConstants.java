import java.util.HashMap;

public class ICMPTypeConstants {

    public final static HashMap<Integer, String> ICMP_TYPE = new HashMap<Integer, String>();
    
    static
    {
        ICMP_TYPE.put(0  ,"Echo (ping) reply"                   );
        ICMP_TYPE.put(3  ,"Destination Unreachable"             );
        ICMP_TYPE.put(4  ,"Source Quench"                       );
        ICMP_TYPE.put(5  ,"Redirect Message"                    );
        ICMP_TYPE.put(8  ,"Echo (ping) request"                 );
        ICMP_TYPE.put(9  ,"Router Advertisement"                );
        ICMP_TYPE.put(10 ,"Router Solicitation"                 );
        ICMP_TYPE.put(11 ,"Time Exceeded"                       );
        ICMP_TYPE.put(12 ,"Parameter Problem: Bad IP header"    );
        ICMP_TYPE.put(13 ,"Timestamp "                          );
        ICMP_TYPE.put(14 ,"Timestamp Reply"                     );
        ICMP_TYPE.put(15 ,"Information Request"                 );
        ICMP_TYPE.put(16 ,"Information Reply"                   );
        ICMP_TYPE.put(17 ,"Address Mask Request"                );
        ICMP_TYPE.put(18 ,"Address Mask Reply"                  );
        ICMP_TYPE.put(30 ,"Traceroute"                          );
        ICMP_TYPE.put(42 ,"Extended Echo Request"               );
        ICMP_TYPE.put(43 ,"Extended Echo Reply"                 );
        ICMP_TYPE.put(200,"Echo (ping) request"                 );
    }

}