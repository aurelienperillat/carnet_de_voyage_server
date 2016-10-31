package com.example.aurel.voyage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/files")
public class JerseyFileUpload {

	/*
	 * Upload a File
	 */

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {
		System.out.println("inside !");
		String filePath = Constants.SERVER_UPLOAD_LOCATION_FOLDER	+ contentDispositionHeader.getFileName();
		System.out.println(filePath);
		// save the file to the server
		saveFile(fileInputStream, filePath);

		String output = "File saved to server location : " + filePath;

		return Response.status(200).entity(output).build();

	}
	
	@GET
	@Path("/download")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response downloadFile(@QueryParam("filename") String filename) {
		System.out.println("file download !");
		StreamingOutput fileStream =  new StreamingOutput() 
        {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException 
            {
                try
                {
                    java.nio.file.Path path = Paths.get(Constants.SERVER_UPLOAD_LOCATION_FOLDER+filename);
                    byte[] data = Files.readAllBytes(path);
                    System.out.println("dwnload file : "+data.length);
                    output.write(data);
                    output.flush();
                } 
                catch (Exception e) 
                {
                	e.printStackTrace();
                    throw new WebApplicationException();
                }
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename = myImage.jpg")
                .build();
	}

	// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream,
			String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
