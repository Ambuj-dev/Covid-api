package com.covid19.covid19api.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import org.springframework.stereotype.Service;

@Service
public class FileDownloadClient {

	public void downloadFile( String fileURL, String fileName ) throws IOException {
		URL url = new URL( fileURL );
		try ( ReadableByteChannel readableByteChannel = Channels.newChannel( url.openStream() );
				FileOutputStream fileOutputStream = new FileOutputStream( fileName );
				FileChannel fileChannel = fileOutputStream.getChannel() ) {

			fileChannel.transferFrom( readableByteChannel, 0, Long.MAX_VALUE );
		}
	}
}
