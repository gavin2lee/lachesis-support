package com.lachesis.support.auth.demo.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

public class PlainNioClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		long count = 0;
		
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		
		
		Selector selector = Selector.open();
		sc.register(selector, SelectionKey.OP_READ);
		
		sc.connect(new InetSocketAddress("localhost", 10080));
		
		while(!sc.finishConnect()){
			System.out.println("please check the connection");
		}
		
		while(true){
			String s = (count++)+" hi server,"+ (new Date().toString());
			ByteBuffer bb = ByteBuffer.wrap(s.getBytes("UTF-8"));
			sc.write(bb);
			int n = selector.select();
			if(n < 1){
				continue;
			}
			
			Iterator<SelectionKey> selectorIter = selector.selectedKeys().iterator();
			while(selectorIter.hasNext()){
				SelectionKey key = selectorIter.next();  
				selectorIter.remove();
				
				if(key.isReadable()){
					SocketChannel ssc = (SocketChannel) key.channel();
					ByteBuffer buf = ByteBuffer.allocate(8);
					StringBuilder sb = new StringBuilder();
					
					while(ssc.read(buf) > 0){
						buf.flip();
						sb.append(buf.array());
						buf.clear();
					}
					
					System.out.println("read: " + sb.toString());
					
				}
			}
		}
	}

}
