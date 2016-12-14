package com.lachesis.support.auth.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	private final int port;
	
	public EchoServer(int port){
		this.port = port;
	}
	
	public void start(){
		EventLoopGroup group = new NioEventLoopGroup();
		
		ServerBootstrap b = new ServerBootstrap();
		
		b.group(group);
		b.channel(NioServerSocketChannel.class);
		b.localAddress(port);
		b.childHandler(new ChannelInitializer<Channel>(){

			@Override
			protected void initChannel(Channel ch) throws Exception {
				
			}
			
		});
	}
}
