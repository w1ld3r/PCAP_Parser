import java.util.HashMap;

public class DNSTypeConstants {

    public final static HashMap<Integer, String> DNS_TYPE = new HashMap<Integer, String>();
    
    static
    {
        DNS_TYPE.put(1      ,"A"            );
        DNS_TYPE.put(28     ,"AAAA"        );
        DNS_TYPE.put(18     ,"AFSDB"        );
        DNS_TYPE.put(42     ,"APL"          );
        DNS_TYPE.put(257    ,"CAA"          );
        DNS_TYPE.put(60     ,"CDNSKEY"      );
        DNS_TYPE.put(59     ,"CDS"          );
        DNS_TYPE.put(37     ,"CERT"         );
        DNS_TYPE.put(5      ,"CNAME"        );
        DNS_TYPE.put(62     ,"CSYNC"        );
        DNS_TYPE.put(49     ,"DHCID"        );
        DNS_TYPE.put(32769  ,"DLV"          );
        DNS_TYPE.put(39     ,"DNAME"        );
        DNS_TYPE.put(48     ,"DNSKEY"       );
        DNS_TYPE.put(43     ,"DS"           );
        DNS_TYPE.put(55     ,"HIP"          );
        DNS_TYPE.put(45     ,"IPSECKEY"     );
        DNS_TYPE.put(25     ,"KEY"          );
        DNS_TYPE.put(36     ,"KX"           );
        DNS_TYPE.put(29     ,"LOC"          );
        DNS_TYPE.put(15     ,"MX"           );
        DNS_TYPE.put(35     ,"NAPTR"        );
        DNS_TYPE.put(2      ,"NS"           );
        DNS_TYPE.put(47     ,"NSEC"         );
        DNS_TYPE.put(50     ,"NSEC3"        );
        DNS_TYPE.put(51     ,"NSEC3PARAM"   );
        DNS_TYPE.put(61     ,"OPENPGPKEY"   );
        DNS_TYPE.put(12     ,"PTR"          );
        DNS_TYPE.put(46     ,"RRSIG"        );
        DNS_TYPE.put(17     ,"RP"           );
        DNS_TYPE.put(24     ,"SIG"          );
        DNS_TYPE.put(53     ,"SMIMEA"       );
        DNS_TYPE.put(6      ,"SOA"          );
        DNS_TYPE.put(33     ,"SRV"          );
        DNS_TYPE.put(44     ,"SSHFP"        );
        DNS_TYPE.put(32768  ,"TA"           );
        DNS_TYPE.put(249    ,"TKEY"         );
        DNS_TYPE.put(52     ,"TLSA"         );
        DNS_TYPE.put(250    ,"TSIG"         );
        DNS_TYPE.put(16     ,"TXT"          );
        DNS_TYPE.put(256    ,"URI"          );
        DNS_TYPE.put(255    ,"ANY"          );
    }
}