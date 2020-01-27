# PCAP PARSER

PCAP Parser is a pcap parser using standards java libraries.

## Prerequisites

Minimum requirement: Java 8 JRE 
```console
sudo apt install openjdk-8-jre
```
Compiler requirement: Java 8 JDK 
```console
sudo apt install openjdk-8-jdk
```

Tested with:
- openjdk 14-ea 2020-03-17
- OpenJDK Runtime Environment (build 14-ea+19-Debian-1)
- OpenJDK 64-Bit Server VM (build 14-ea+19-Debian-1, mixed mode, sharing)


## Compile

```console
javac -d bin src/*.java
```

## Usage

Display all packets from a pcap file:
```console
java -cp bin PCAP_Parser -f file.pcap
```

Display all packets of a specified protocol from a pcap file:
```console
java -cp bin PCAP_Parser -f file.pcap -p protocol_name
```

Display all packets from a pcap file and write each TCP stream to a specific file:
```console
java -cp bin PCAP_Parser -f file.pcap -s
```

## Protocols supported

List of protocols supporded:
- Ethernet (eth)
- Internet Protocol v4 (ip)
- Address Resolution Protocol (arp)
- Internet Control Message Protocol (icmp)
- User Datagram Protocol (udp)
- Transmission Control Protocol (tcp)
- Domain Name System (udp)
- Dynamic Host Configuration Protocol (dhcp)
- File Transfer Protocol (ftp)
- Hypertext Transfer Protocol (http)

## Authors

* **[x1n5h3n](https://github.com/x1n5h3n)**

## License

This project is licensed under the GPLv3 License - see the [LICENSE](LICENSE) file for details.
