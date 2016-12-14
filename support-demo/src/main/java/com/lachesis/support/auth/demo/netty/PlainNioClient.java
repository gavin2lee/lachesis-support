package com.lachesis.support.auth.demo.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class PlainNioClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		
		
		Selector selector = Selector.open();
		sc.register(selector, SelectionKey.OP_READ);
		
		sc.connect(new InetSocketAddress("localhost", 10080));
		
		while(!sc.finishConnect()){
			System.out.println("please check the connection");
		}
		
		while(true){
			int n = selector.select();
			if(n < 1){
				continue;
			}
			
			Iterator<SelectionKey> selectorIter = selector.selectedKeys().iterator();
			while(selectorIter.hasNext()){
				SelectionKey key = selectorIter.next();  
				selectorIter.remove();
				
				if(key.isAcceptable()){
					SocketChannel client = (SocketChannel) key.channel();
				}
			}
		}
	}

}
