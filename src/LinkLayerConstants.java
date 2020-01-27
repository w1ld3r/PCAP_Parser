import java.util.HashMap;

// Constants link layer enum
public class LinkLayerConstants {
	
	public final static HashMap<Integer, String> LINK_LAYER_LIST = new HashMap<Integer, String>();
	
	static
	{
		LINK_LAYER_LIST.put(0  ,"NULL"                  );
		LINK_LAYER_LIST.put(1  ,"ETHERNET"              );
		LINK_LAYER_LIST.put(2  ,"EXP_ETHERNET"          );
		LINK_LAYER_LIST.put(3  ,"AX25"                  );
		LINK_LAYER_LIST.put(4  ,"PRONET"                );
		LINK_LAYER_LIST.put(5  ,"CHAOS"                 );
		LINK_LAYER_LIST.put(6  ,"TOKEN_RING"            );
		LINK_LAYER_LIST.put(7  ,"ARCNET"                );
		LINK_LAYER_LIST.put(8  ,"SLIP"                  );
		LINK_LAYER_LIST.put(9  ,"PPP"                   );
		LINK_LAYER_LIST.put(10 ,"FDDI"                  );
		LINK_LAYER_LIST.put(50 ,"PPP_HDLC"              );
		LINK_LAYER_LIST.put(51 ,"PPP_ETHER"             );
		LINK_LAYER_LIST.put(99 ,"SYMANTEC_FIREWALL"     );
		LINK_LAYER_LIST.put(100,"ATM_RFC1483"           );
		LINK_LAYER_LIST.put(101,"RAW"                   );
		LINK_LAYER_LIST.put(102,"SLIP_BSDOS"            );
		LINK_LAYER_LIST.put(103,"PPP_BSDOS"             );
		LINK_LAYER_LIST.put(104,"C_HDLC"                );
		LINK_LAYER_LIST.put(105,"IEEE802_11"            );
		LINK_LAYER_LIST.put(106,"ATM_CLIP"              );
		LINK_LAYER_LIST.put(107,"FRELAY"                );
		LINK_LAYER_LIST.put(108,"LOOP"                  );
		LINK_LAYER_LIST.put(109,"ENC"                   );
		LINK_LAYER_LIST.put(110,"LANE8023"              );
		LINK_LAYER_LIST.put(111,"HIPPI"                 );
		LINK_LAYER_LIST.put(112,"HDLC"                  );
		LINK_LAYER_LIST.put(113,"LINUX_SLL"             );
		LINK_LAYER_LIST.put(114,"LTALK"                 );
		LINK_LAYER_LIST.put(115,"ECONET"                );
		LINK_LAYER_LIST.put(116,"IPFILTER"              );
		LINK_LAYER_LIST.put(117,"PFLOG"                 );
		LINK_LAYER_LIST.put(118,"CISCO_IOS"             );
		LINK_LAYER_LIST.put(119,"PRISM_HEADER"          );
		LINK_LAYER_LIST.put(120,"AIRONET_HEADER"        );
		LINK_LAYER_LIST.put(121,"HHDLC"                 );
		LINK_LAYER_LIST.put(122,"IP_OVER_FC"            );
		LINK_LAYER_LIST.put(123,"SUNATM"                );
		LINK_LAYER_LIST.put(124,"RIO"                   );
		LINK_LAYER_LIST.put(125,"PCI_EXP"               );
		LINK_LAYER_LIST.put(126,"AURORA"                );
		LINK_LAYER_LIST.put(127,"IEEE802_11_RADIOTAP"   );
		LINK_LAYER_LIST.put(128,"TZSP"                  );
		LINK_LAYER_LIST.put(129,"ARCNET_LINUX"          );
		LINK_LAYER_LIST.put(130,"JUNIPER_MLPPP"         );
		LINK_LAYER_LIST.put(131,"JUNIPER_MLFR"          );
		LINK_LAYER_LIST.put(132,"JUNIPER_ES"            );
		LINK_LAYER_LIST.put(133,"JUNIPER_GGSN"          );
		LINK_LAYER_LIST.put(134,"JUNIPER_MFR"           );
		LINK_LAYER_LIST.put(135,"JUNIPER_ATM2"          );
		LINK_LAYER_LIST.put(136,"JUNIPER_SERVICES"      );
		LINK_LAYER_LIST.put(137,"JUNIPER_ATM1"          );
		LINK_LAYER_LIST.put(138,"APPLE_IP_OVER_IEEE1394");
		LINK_LAYER_LIST.put(139,"MTP2_WITH_PHDR"        );
		LINK_LAYER_LIST.put(140,"MTP2"                  );
		LINK_LAYER_LIST.put(141,"MTP3"                  );
		LINK_LAYER_LIST.put(142,"SCCP"                  );
		LINK_LAYER_LIST.put(143,"DOCSIS"                );
		LINK_LAYER_LIST.put(144,"LINUX_IRDA"            );
		LINK_LAYER_LIST.put(145,"IBM_SP"                );
		LINK_LAYER_LIST.put(146,"IBM_SN"                );
	}
}