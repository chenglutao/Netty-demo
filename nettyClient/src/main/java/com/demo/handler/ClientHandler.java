package com.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author chenglutao
 * @date 2019-12-02 16:13
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 本方法用于向服务端发送信息
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        String msg = "hello Server! I am Client";
        ByteBuf encoded = ctx.alloc().buffer(msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    /**
     * 本方法用于接收服务端发送过来的消息
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("ClientHandler.channelRead");
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        log.info("Server said: {}", new String(bytes));
        byteBuf.release();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
