package com.troy.diploclient.net;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLException;

import com.troy.diplo.game.DiploConstants;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.*;
import io.netty.handler.ssl.*;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * Modification of {@link EchoClient} which utilizes Java object serialization.
 */
public final class DiploClientNet {

	protected AtomicBoolean connected = new AtomicBoolean(false);
	private ChannelFuture future;
	private EventLoopGroup group;
	private SslContext sslCtx;
	private DiploClientNetHandler handler;

	public DiploClientNet() {
		final DiploClientNet thisObj = this;
		new Thread(() -> {
			if (DiploConstants.USE_SSL) {
				try {
					sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
				} catch (SSLException e1) {
					e1.printStackTrace();
				}
			}
			do {
				group = new NioEventLoopGroup(2);
				try {
					Bootstrap b = new Bootstrap();
					b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							if (DiploConstants.USE_SSL) p.addLast(sslCtx.newHandler(ch.alloc(), DiploConstants.ADDRESS.getHostAddress(), DiploConstants.PORT));
							handler = new DiploClientNetHandler(thisObj);
							p.addLast(new ObjectEncoder(), new ObjectDecoder(ClassResolvers.cacheDisabled(null)), handler);
						}
					});
					System.out.println("trying to connect...");
					// Start the connection attempt.
					future = b.connect(DiploConstants.ADDRESS, DiploConstants.PORT).sync();
					connected.set(true);
					System.out.println("connected to server!");
				} catch (InterruptedException e) {
					System.out.println("interupted while waiting for connection to close");
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("Network Handler unsble to connect!");
					com.troyberry.util.ThreadUtils.sleep(2000);
				}
			}

			while (!connected.get());

		}, "Client Network-Thread").start();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> close()));
	}
	
	public ChannelHandlerContext getContext() {
		if(handler == null) throw new IllegalStateException("Handler is null! Socket not set up yet! connected? " + connected);
		return handler.getCtx();
	}

	public void close() {
		if (connected.get()) {
			try {
				System.out.println("Closing network handler!");
				connected.set(false);
				if (future != null)
					future.channel().close();

			} catch (Exception e) {
				// ignore because we are closing
			}
		}
		if (group != null) {
			group.shutdownGracefully();
			group = null;
		}
	}

	public boolean isConnected() {
		return connected.get();
	}
}
