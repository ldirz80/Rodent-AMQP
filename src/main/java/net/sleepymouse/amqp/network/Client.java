package net.sleepymouse.amqp.network;

import java.net.Socket;

import net.sleepymouse.amqp.AMQPConstants;

public class Client
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Client client = new Client();
		client.send();
	}

	public Client()
	{}

	public void send()
	{
		try
		{
			Socket s = new Socket("127.0.0.1", AMQPConstants.PORT);

			byte[] data = { 65, 77, 81, 80, (byte) 0b1101_0000, 0x01, 0x00, 0x00, //
					0x00, 0x00, 0x00, 0x02, 65, 66, //
					0x00, 0x00, 0x00, 0x02, 67, 68, //
					0x00, 0x00, 0x00, 0x02, 69, 70 };

			s.getOutputStream().write(data);
			s.close();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
