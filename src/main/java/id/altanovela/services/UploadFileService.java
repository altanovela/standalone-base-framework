package id.altanovela.services;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import id.altanovela.util.RandomStringUtil;

@Service
public class UploadFileService {

    @Value("${upload.file.root.path}")
    private String LOCAL_PATH;

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg");
    
    /**
     * Upload Image File
     * @param file
     * @return
     */
    public String uploadImageFile(MultipartFile file) {
        String PROFILE_PICT_PATH = "/metrasrc/profil/pict/";
        String PROFILE_PICT_FULL_PATH = LOCAL_PATH + PROFILE_PICT_PATH;
        
        try {
            String filename = imageFileName(file);
            if(uploadFile(file, PROFILE_PICT_FULL_PATH + filename) == null){
                return  "";
            }
            return PROFILE_PICT_PATH + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Upload File
     * @param file
     * @param filePath
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file, String fullFilePath) throws IOException {

        byte barr[] = null;
        BufferedOutputStream bos = null;
        try {
            String fileContentType = file.getContentType();
            if(!contentTypes.contains(fileContentType)) {
                return null;
            }
            barr = file.getBytes();
            bos  = new BufferedOutputStream(new FileOutputStream(fullFilePath));
            bos.write(barr);
        } catch(Exception e){
            System.out.println(e);
        } finally {
            if(null != bos) {
                bos.flush();  
                bos.close();
            }
        }
        
        return fullFilePath;
    }
    
    /**
     * Generate Image Filename
     * @param file
     * @return
     */
    public String imageFileName(MultipartFile file) {
        RandomStringUtil randomUtil = new RandomStringUtil(16);
        return StringUtils.lowerCase(System.currentTimeMillis() + "_" + randomUtil.nextString() + getExt(file.getOriginalFilename()));
    }
    
    private String getExt(String d) {
        String n = StringUtils.trimToEmpty(d);
        if(n.contains(".")) {
            return n.substring(n.lastIndexOf("."));
        }
        return "";
    }
    
    /**
     * Validate Image
     * @param file
     * @return
     */
    public boolean isEligibleImage(MultipartFile file) {
        return 
                isEligibleType(file) &&
                isEligibleSize(file);
    }
    
    public boolean isfile(MultipartFile file) {
        return !file.isEmpty() && file.getSize() > 0;
    }
    
    /**
     * Validate File Type, should be jpg, jpeg, png
     * @param file
     * @return
     */
    public boolean isEligibleType(MultipartFile file) {; 
        if(contentTypes.contains(file.getContentType().toLowerCase())){
            return true;
        }
        return false;
    }
    
    /**
     * Validate Max File Size, should be less than 1 MegaBytes
     * @param file
     * @return
     */
    public boolean isEligibleSize(MultipartFile file) {
        // 1.000.000 Bytes = 1 MegaBytes (in decimal)
        if(file.getSize() < 1000000){
            return true;
        }
        return false;
    }
}
