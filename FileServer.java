import java.io.*;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FileServer  implements FileInterface{
   
//Sends the requested file to the client
	@Override
	public byte[] downloadOp(String serverPath){
	      try {
	         File file = new File(serverPath);
	         byte fileData[] = new byte[(int)file.length()];
	         FileInputStream fis = new FileInputStream(serverPath);
	         BufferedInputStream bis = new BufferedInputStream(fis);
	         bis.read(fileData,0,fileData.length);
	         bis.close();
	         System.out.println("Server Operation: Downloading File - Complete");
	         return(fileData);
	      } catch(Exception e){
	    	 System.out.println("Server Operation: Downloading File - Unsuccesful");
	         System.out.println("Server Error "+e.getMessage());
	         e.printStackTrace();
	         return null;
	      }
	}
	
//Receives the requested file from the client and stores it
	@Override
	public void uploadOp(String serverPath, byte[] b ) throws IOException
	{
		try {
		byte[] fileData = b;
		FileOutputStream fos = new FileOutputStream(serverPath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(fileData,0,fileData.length);
        bos.flush();
        bos.close();
        System.out.println("Server Operation: Uploading file - Complete");
        }
		catch(Exception e){
			System.out.println("Server Operation: Uploading File - Unsuccesful");
			System.out.println("Server Error: "+e.getMessage());
	        e.printStackTrace();
		}
	}
	     
//Lists all the files and directories in a given directory
	@Override
	public String listDir(String serverPath) throws IOException {
	          
		   
		    	String filesList;
			    filesList = "";
				File[] list;
				 try {
					 File myFile = new File(serverPath);
				  if(myFile.isDirectory())
				  { 
					filesList = "The directory list: \n";
				  	list = myFile.listFiles();
				    for (int i = 0; i < list.length; i++) 
				      {
				          if (list[i].isFile()) 
				             filesList += "File - "+ list[i].getName() + "\n";
				           else if (list[i].isDirectory()) 
				             filesList += "Directory - " + list[i].getName() + "\n";
				       }
				    System.out.println("Server Operation: List Directory - Complete");
				  }
				  else
				  {
				  	filesList +="Directory does not exist";
				  	System.out.println("Server Operation: List Directory - Unsuccesful");
				  }
				  
			} catch (Exception e) {
				System.out.println("Server Operation: List Directory - Unsuccesful");
				System.out.println("Server Error: "+e.getMessage());
				e.printStackTrace();
			}
	          return filesList;
	          
	      }
 
	

//Creates a directory in the specified location
	@Override
	public String mkdirOp(String serverPath) throws IOException
	  {	
		File myFile = new File(serverPath);
		String result = "";  
		try 
		{
			if(!myFile.isDirectory())
			{	
				myFile.mkdir();
				result += "Directory Created";
			}
			else
			{
				result += "Directory already exists"; 
		    }
			System.out.println("Server Operation: Create Directory - Complete");
		} catch (Exception e) {
			System.out.println("Server Operation: Create Directory - Unsuccesful");
			System.out.println("Server Error: "+e.getMessage());
			e.printStackTrace();
			}
		return result;
    }

//Deletes the requested directory if empty
	@Override
	public String rmdirOp(String serverPath) throws IOException
	   {
	      	
	   	File myFile = new File(serverPath);
	   	String result = "";
	   	try 
	   	{
	   		if(myFile.isDirectory()&& myFile.list().length == 0)
	   		{	
	   			myFile.delete();
	   			result += "Directory deleted";
	   	   	}
	   		else
	   		{
	   			result += "Directory is not empty";
	  		 }
	   		System.out.println("Server Operation: Remove Directory - Complete");
		} catch (Exception e) {
			System.out.println("Server Operation: Remove Directory - Unsuccesful");
			System.out.println("Server Error: "+e.getMessage());
			e.printStackTrace();
			}
		return result;
	  }

//Deletes the requested file 
	@Override
	public String rmFileOp(String serverPath) throws IOException
	   {
		String result = "";
	   	File myFile = new File(serverPath);
	   	try 
	   	{
	   		if(myFile.exists()&& myFile.isFile())
	    	{	
	   			myFile.delete();
	   	    	result = "File deleted";
		   	}
			else
	   		{
	   			result = "File Not Found";
	   		}
	   		System.out.println("Server Operation: Remove File - Complete");
		} catch (Exception e) {
			System.out.println("Server Operation: Remove File - Unsuccesful");
			System.out.println("Server Error: "+e.getMessage());
			e.printStackTrace();
			}
		return result;
     }
	
//Terminates the server
	@Override
	public void shutdown(Registry registry ) throws IOException
	{
		
		try 
		{
			registry.unbind("FileInterface");
			
			System.out.println("Server Terminated");
			FileServer fileserver = new FileServer();
			fileserver.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		System.exit(0);
	}
	
	public static void main(String args[]) {
    try {
    	if(args[0].equalsIgnoreCase("start"))
    	{	
    	 FileServer fs =new FileServer(); 
    	 FileInterface fi = (FileInterface)UnicastRemoteObject.exportObject(fs,Integer.parseInt(args[1]) );
         Registry registry = LocateRegistry.getRegistry();
         registry.rebind("FileInterface", fi);
         System.out.println("Server Running");
    	}
      } catch(RemoteException e) {
         System.out.println("FileServer: "+e.getMessage());
         e.printStackTrace();
         System.exit(0);
      }
   }
}