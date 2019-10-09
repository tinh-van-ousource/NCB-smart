package com.tvo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.tvo.common.DateTimeUtil;
import com.tvo.common.FileStorageException;
import com.tvo.config.FileBannerStorageProperties;
import com.tvo.config.FileStorageProperties;
import com.tvo.response.UploadFileResponse;

@Service
public class FileServiceImpl implements FileService {

	private final Path fileStorageLocation;
	private final Path fileBannerStorageLocation;

	@Autowired
	public FileServiceImpl(FileStorageProperties fileStorageProperties,
			FileBannerStorageProperties fileBannerStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		this.fileBannerStorageLocation = Paths.get(fileBannerStorageProperties.getUploadDir()).toAbsolutePath()
				.normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
			Files.createDirectories(this.fileBannerStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	@Override
	public UploadFileResponse uploadFile(MultipartFile file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date()) +  file.getOriginalFilename() ;
 
		String fileUploadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/").path(fileName)
				.toUriString();
		String user = "cmsuat";
		String pass = "123@123aA";
		String sharedFolder = "CMSBanner";
//		String sharedFolder = "Banner";
		SMBClient client = new SMBClient();

		try (Connection connection = client.connect("10.1.62.33")) {
//		try (Connection connection = client.connect("10.52.20.10")) {
			AuthenticationContext ac = new AuthenticationContext(null, null, "");
			Session session = connection.authenticate(ac);
			System.out.println("connect ok");
			// Connect to Share
			OutputStream out = null;
			try {
				DiskShare share = (DiskShare) session.connectShare(sharedFolder);
				Set<FileAttributes> fileAttributes = new HashSet<>();
	            fileAttributes.add(FileAttributes.FILE_ATTRIBUTE_NORMAL);
	            Set<SMB2CreateOptions> createOptions = new HashSet<>();
	            createOptions.add(SMB2CreateOptions.FILE_RANDOM_ACCESS);
	           com.hierynomus.smbj.share.File openFile = share.openFile("NewSmart/" + fileName, new HashSet(Arrays.asList(new AccessMask[]{AccessMask.GENERIC_ALL})), fileAttributes, SMB2ShareAccess.ALL, SMB2CreateDisposition.FILE_OVERWRITE_IF, createOptions);
	            
				out= openFile.getOutputStream();
				out.write(file.getBytes());
				out.flush();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(out != null) {
					out.close();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			client.close();
		}
		return new UploadFileResponse(fileName, fileUploadUri);
	}

	@Override
	public UploadFileResponse uploadBannerFile(MultipartFile file) {
		try {
			String fileName = storeFile(this.fileBannerStorageLocation, file);

			String fileUploadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/").path(fileName)
					.toUriString();

			return new UploadFileResponse(fileName, fileUploadUri);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String storeFile(Path path, MultipartFile file) {
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS");

		// Normalize file name
		String fileName = file.getOriginalFilename();
		fileName = currentUserName + "_" + now.format(formatter) + "_" + fileName;
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = path.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (Exception ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	@Override
	public String getImagePath(String directory, String imageName) {
		File file = new File(directory + imageName);
		return file.getAbsolutePath();
	}

	@Override
	public boolean deleteImage(String directory, String imageName) {
		File file = new File(directory + imageName);
		return file.delete();
	}

}
