package com.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenglutao
 */
@Slf4j
public class SendMessageOutboundHandler extends ChannelOutboundHandlerAdapter {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg,
                      ChannelPromise promise) throws Exception {

        String response = "1234567890";
        ByteBuf buf = ctx.alloc().buffer(response.length());
        log.info("下发的信息为："+ response);
        buf.writeBytes(response.getBytes());
        ctx.writeAndFlush(buf).addListener(new ChannelFutureListener(){
            @Override
            public void operationComplete(ChannelFuture future)
                    throws Exception {
                log.info("下发成功！");
            }
        });
    }
}
