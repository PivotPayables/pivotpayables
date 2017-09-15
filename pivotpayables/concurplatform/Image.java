
package com.pivotpayables.concurplatform;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;



/**
 * @author John Toman
 * 3/22/2015
 * 
 * This is the base class for an Image
 * 
 * Its methods provide functions for downloading an image file using an Image URL as well as methods for storing and reading image files
 *
 */
public  class Image {
	public static void downloadImageFile(String downloadimageurl, String imagefilepath) throws MalformedURLException, IOException
	{
	    URL url = new URL(downloadimageurl);
	    Files.copy(url.openStream(), new File(imagefilepath).toPath(),
	            StandardCopyOption.REPLACE_EXISTING);// download the image file using the URL; store the file in the directory specified by imagefilepath 
	}
	public static byte[] readFS(String downloadpath, String filename, String type) throws IOException {

		File file = new File(downloadpath + filename);
		BufferedImage originalImage=ImageIO.read(file);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ImageIO.write(originalImage, type, baos );
		byte[] imageInByte=baos.toByteArray();
        
        return imageInByte;
        
	}
	public static void writeFS (byte[] bytes, String downloadpath, String filename) throws IOException {
 
		File file = new File(downloadpath + "NEW" + filename);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.flush();
        fos.close();

		/* reads image file from disk, super-fast way: read, http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly
		FileInputStream f = new FileInputStream( name );
		FileChannel ch = f.getChannel( );
		long size = ch.size();
		MappedByteBuffer mb = ch.map(MapMode.READ_ONLY, 0L,size);
		int filesize = (int)size;
		byte[] barray = new byte[filesize];// set byte array to the file size
		int nGet;
		while( mb.hasRemaining( ) )
		{
		    nGet = Math.min( mb.remaining( ), 8192 );
		    mb.get( barray, 0, nGet );
		}

		return mb.g
				*/
	}


}
