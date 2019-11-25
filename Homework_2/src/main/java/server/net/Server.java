package server.net;

import com.sun.xml.internal.ws.api.message.AttachmentEx;
import server.controller.GameHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private InetSocketAddress hostAddress;

    public Server() {
        this.start();
    }

    private void start() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            hostAddress = new InetSocketAddress("localhost", 1337);
            serverSocketChannel.bind(hostAddress);
            setNoneBlocking(serverSocketChannel);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void select() {
        try {
            selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SocketChannel connectionAccept(ServerSocketChannel channel) {
        try {
            return channel.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void disconnect(SocketChannel channel) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setConnectable(SocketChannel channel) {
        try {
            channel.register(selector, SelectionKey.OP_WRITE,new Attachment("start"));
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    private String getLocalAdress(SocketChannel channel) {
        try {
            return channel.getLocalAddress().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String readChannel(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        if (client.isConnected()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
                client.read(buffer);
                return new String(buffer.array()).trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void writeToChannel(SelectionKey key, String message) {
        SocketChannel client = (SocketChannel) key.channel();
        try {
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            client.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNoneBlocking(SocketChannel channel) {
        try {
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNoneBlocking(ServerSocketChannel channel) {
        try {
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void listen() {
        print("I am up and running!");
        while (true) {
            print("Awaiting work");
            select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey currentKey = keyIterator.next();
                keyIterator.remove();
                if (currentKey.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) currentKey.channel();
                    SocketChannel currentChannel = connectionAccept(server);
                    setNoneBlocking(currentChannel);
                    setConnectable(currentChannel);
                    print("Accepted Connection from: " + getLocalAdress(currentChannel));
                } else if (currentKey.isReadable()) {
                    String input = readChannel(currentKey);
                    if (input != null) {
                    Attachment attachment = (Attachment) currentKey.attachment();
                    attachment.gameHandler.messageHandler(input);
                    currentKey.interestOps(SelectionKey.OP_WRITE);
                    } else {
                    disconnect((SocketChannel) currentKey.channel());
                    }
                } else if (currentKey.isWritable()) {
                    Attachment attachment = (Attachment) currentKey.attachment();
                    if (attachment.state.equals("start")) {
                        attachment.start(currentKey,selector);
                      //  writeToChannel(currentKey, attachment.gameHandler.getStartState());
                    } else {
                        writeToChannel(currentKey, attachment.gameHandler.getState());
                    }
                    currentKey.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }


}
