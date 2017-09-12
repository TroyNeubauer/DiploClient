package com.troy.diploclient.net;

import io.netty.channel.*;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 */
public class DiploClientNetHandler extends ChannelInboundHandlerAdapter {

	private DiploClientNet net;
	private ChannelHandlerContext ctx;
	
    /**
     * Creates a client-side handler.
     */
    public DiploClientNetHandler(DiploClientNet net) {
    	this.net = net;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    	this.ctx = ctx;
    	net.connected.set(true);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Client recieved message from server " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
}
